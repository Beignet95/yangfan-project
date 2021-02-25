package com.ruoyi.project.oms.transactionRecord.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.ruoyi.common.utils.csv.CsvUtil;
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
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        CsvUtil<TransactionRecord> util = new CsvUtil<TransactionRecord>(TransactionRecord.class);
        List<TransactionRecord> transactionRecordList = util.importCvs(file.getInputStream(),7);
        String message = transactionRecordService.importTransactionRecord(transactionRecordList, updateSupport);
        return AjaxResult.success(message);
    }
}