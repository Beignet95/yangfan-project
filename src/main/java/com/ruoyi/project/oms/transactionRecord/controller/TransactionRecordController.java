package com.ruoyi.project.oms.transactionRecord.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.csv.CsvUtil;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecordImpTempVo;
import com.ruoyi.project.oms.transactionRecord.vo.BLDTransactionRecordVo;
import com.ruoyi.project.oms.transactionRecord.vo.FinanceVo;
import com.ruoyi.project.oms.transactionRecord.vo.OrderIdSku;
import com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecord;
import com.ruoyi.project.oms.transactionRecord.service.ITransactionRecordService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 交易数据Controller
 * 
 * @author Beignet
 * @date 2021-02-23
 */
@Controller
@RequestMapping("/oms/transactionRecord")
public class TransactionRecordController extends BaseController
{
    private String prefix = "oms/transactionRecord";

    @Autowired
    private ITransactionRecordService transactionRecordService;

    @RequiresPermissions("oms:transactionRecord:view")
    @GetMapping()
    public String transactionRecord()
    {
        return prefix + "/transactionRecord";
    }

    @RequiresPermissions("oms:transactionRecord:view")
    @GetMapping("/US")
    public String transactionRecordOfUS()
    {
        return prefix + "/transactionRecord-us";
    }

    @RequiresPermissions("oms:transactionRecord:view")
    @GetMapping("/truckRecord")
    public String truckRecord()
    {
        return prefix + "/transactionRecord-truckrecord";
    }

    @RequiresPermissions("oms:transactionRecord:view")
    @GetMapping("/BDAndLDReocord")
    public String BDAndLDReocord()
    {
        return prefix + "/transactionRecord-bdandldrecord";
    }

    @RequiresPermissions("oms:transactionRecord:view")
    @GetMapping("/feeAdjustmentReocord")
    public String feeAdjustmentReocord()
    {
        return prefix + "/transactionRecord-fee-adjustment-record";
    }

    /**
     * 查询交易数据列表
     */
    @RequiresPermissions("oms:transactionRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TransactionRecord transactionRecord)
    {
        startPage();
        List<TransactionRecord> list = transactionRecordService.selectTransactionRecordList(transactionRecord);
        return getDataTable(list);
    }

    /**
     * 导出交易数据列表
     */
    @RequiresPermissions("oms:transactionRecord:export")
    @Log(title = "交易数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TransactionRecord transactionRecord)
    {
        List<TransactionRecord> list = transactionRecordService.selectTransactionRecordList(transactionRecord);
        ExcelUtil<TransactionRecord> util = new ExcelUtil<TransactionRecord>(TransactionRecord.class);
        return util.exportExcel(list, "transactionRecord");
    }

    /**
     * 新增交易数据
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存交易数据
     */
    @RequiresPermissions("oms:transactionRecord:add")
    @Log(title = "交易数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TransactionRecord transactionRecord)
    {
        return toAjax(transactionRecordService.insertTransactionRecord(transactionRecord));
    }

    /**
     * 修改交易数据
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TransactionRecord transactionRecord = transactionRecordService.selectTransactionRecordById(id);
        mmap.put("transactionRecord", transactionRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存交易数据
     */
    @RequiresPermissions("oms:transactionRecord:edit")
    @Log(title = "交易数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TransactionRecord transactionRecord)
    {
        return toAjax(transactionRecordService.updateTransactionRecord(transactionRecord));
    }

    /**
     * 删除交易数据
     */
    @RequiresPermissions("oms:transactionRecord:remove")
    @Log(title = "交易数据", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(transactionRecordService.deleteTransactionRecordByIds(ids));
    }

    @Log(title = "交易数据导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("oms:transactionRecord:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport,Date month,String account,String site,String spareField) throws Exception
    {

        if (StringUtils.isEmpty(account)||StringUtils.isEmpty(site)||StringUtils.isEmpty(spareField))
            throw new BusinessException("账号和站点都不能为空！");

        CsvUtil<TransactionRecordImpTempVo> util = new CsvUtil<TransactionRecordImpTempVo>(TransactionRecordImpTempVo.class);
        List<TransactionRecordImpTempVo> impTempVos = util.importCvs(file.getInputStream(),7);
        String message = transactionRecordService.importTransactionRecord(impTempVos, updateSupport,month,account,site,spareField);
        return AjaxResult.success(message);
    }

    /**
     * 交易记录分析页面
     */
    @GetMapping("/analysis")
    @RequiresPermissions("oms:transactionRecord:analysis")
    public String analysis(ModelMap mmap)
    {
        return prefix + "/analysis";
    }

    /**
     * 获取交易记录分析数据
     * @param advertisingAnalyParamVo
     * @param mmap
     * @return
     */
    @PostMapping("/getAnalysisData")
    @RequiresPermissions("oms:transactionRecord:analysis")
    @ResponseBody
    public TableDataInfo getAnalysisData(TransactionRecord transactionRecord, ModelMap mmap) throws ParseException, IllegalAccessException {
        if(checkParams(transactionRecord)){
            Map resultMap = transactionRecordService.selectTransactionAnaly(transactionRecord);
            List<FinanceVo> financeVos = (List<FinanceVo>) resultMap.get("data");
            String msg = (String) resultMap.get("msg");
            TableDataInfo tableDataInfo = getDataTable(financeVos==null?new ArrayList<FinanceVo>():financeVos);
            tableDataInfo.setMsg(msg);
            return tableDataInfo;
        }else return getDataTable(new ArrayList<TransactionRecord>());

    }

    private boolean checkParams(TransactionRecord transactionRecord) {
        String site = transactionRecord.getSite();
        if(StringUtils.isNotEmpty(site)){
            if(site.endsWith("US")||site.endsWith("CA")){
                return StringUtils.isNotEmpty(transactionRecord.getSpareField());
            }else {
                return true;
            }
        }else {
            return false;
        }
    }


    //
    /**
     * 导出交易数据列表
     */
    @RequiresPermissions("oms:transactionRecord:export")
    @Log(title = "交易数据", businessType = BusinessType.EXPORT)
    @PostMapping("/exportGatherData")
    @ResponseBody
    public AjaxResult exportGatherData(TransactionRecord transactionRecord) throws ParseException, IllegalAccessException {
        List<FinanceVo> list = (List<FinanceVo>) transactionRecordService.selectTransactionAnaly(transactionRecord).get("data");
        ExcelUtil<FinanceVo> util = new ExcelUtil<FinanceVo>(FinanceVo.class);
        return util.exportExcel(list, "财务汇总数据");
    }

    /**
     * 查询卡车详细
     */
    @RequiresPermissions("oms:transactionRecord:view")
    @GetMapping("/detail/{truckRecordId}")
    public String detail(@PathVariable("truckRecordId") Long truckRecordId, ModelMap mmap)
    {
        mmap.put("truckRecordId", truckRecordId);
        return "lms/truckService/truckService";
    }

    /**
     * 查询BD与LD记录数据列表
     */
    @RequiresPermissions("oms:transactionRecord:list")
    @PostMapping("/BLDTransactionReocordVoList")
    @ResponseBody
    public TableDataInfo BLDTransactionReocordVoList(BLDTransactionRecordVo vo)
    {
        startPage();
        List<BLDTransactionRecordVo> list = transactionRecordService.selectBLDTransactionReocordVoList(vo);
        return getDataTable(list);
    }

    /**
     *  BLD记录与ASIN关联
     */
    /**
     * 查询BD与LD记录数据列表
     */
    @Log(title = "关联或修改关联ASIN", businessType = BusinessType.UPDATE)
    @RequiresPermissions("oms:transactionRecord:list")
    @PostMapping("/BLDRecordrelateASIN")
    @ResponseBody
    public AjaxResult BLDRecordrelateASIN(Long recordId,String asin)
    {
        return toAjax(transactionRecordService.BLDRecordrelateASIN(recordId,asin));
    }


    /**
     * 导出交易数据列表
     */
    @RequiresPermissions("oms:transactionRecord:export")
    @Log(title = "交易数据", businessType = BusinessType.EXPORT)
    @PostMapping("/exportOrderIdStrs")
    @ResponseBody
    public AjaxResult exportOrderIdStrs(TransactionRecord transactionRecord)
    {
        String orderIdStrs =  transactionRecordService.exportOrderIdStrs(transactionRecord);
        return AjaxResult.success("操作成功",orderIdStrs);
    }

    //导出 订单号与SKU关系模板
    @RequiresPermissions("oms:transactionRecord:export")
    @GetMapping("/importOrderIdSkuTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<OrderIdSku> util = new ExcelUtil<OrderIdSku>(OrderIdSku.class);
        return util.importTemplateExcel("订单号与SKU关系");
    }

    @Log(title = "订单SKU关系导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("oms:transactionRecord:import")
    @PostMapping("/importOrderSku4FeeAdjustmentOrder")
    @ResponseBody
    public AjaxResult importOrderSku4FeeAdjustmentOrder(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<OrderIdSku> util = new ExcelUtil<OrderIdSku>(OrderIdSku.class);
        List<OrderIdSku> impTempVos = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = transactionRecordService.importOrderSku4FeeAdjustmentOrder(impTempVos, updateSupport);
        return AjaxResult.success(message);
    }

    /**
     * 查询交易数据列表
     */
    @RequiresPermissions("oms:transactionRecord:list")
    @PostMapping("/feeAdjustmentRecordlist")
    @ResponseBody
    public TableDataInfo feeAdjustmentRecordlist(TransactionRecord transactionRecord)
    {
        startPage();
        List<TransactionRecord> list = transactionRecordService.selectTransactionRecordList(transactionRecord);
        return getDataTable(list);
    }

    /**
     * 展示导入框
     */
    @GetMapping("/showImportPage")
    public String showImportPage(boolean showSpareField,ModelMap mmap)
    {
        mmap.addAttribute("showSpareField",showSpareField);
        return prefix + "/import";
    }

    /**
     * 解锁并删除数据
     */
    @PostMapping("/unlockData")
    @RequiresPermissions("pms:advertisingFee:remove")
    @Log(title = "解锁交易记录数据", businessType = BusinessType.DELETE)
    @ResponseBody
    public AjaxResult unlockData(Date month, String site,String spareField)
    {
        int unlockNum = transactionRecordService.unlockData(month,site,spareField);
        if(unlockNum>-1){
            return AjaxResult.success("成功结算并删除"+unlockNum+"条数据！");
        }else{
            return AjaxResult.success("数据未曾锁定！无需解锁!");
        }
    }


}
