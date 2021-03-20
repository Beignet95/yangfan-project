package com.ruoyi.project.sms.storageRecord.controller;

import java.util.List;

import com.ruoyi.common.utils.csv.CsvUtil;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecordImpTempVo;
import com.ruoyi.project.sms.storageRecord.vo.StorageRecordImpTempVo;
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
import com.ruoyi.project.sms.storageRecord.domain.StorageRecord;
import com.ruoyi.project.sms.storageRecord.service.IStorageRecordService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 仓储记录Controller
 * 
 * @author Beignet
 * @date 2021-03-06
 */
@Controller
@RequestMapping("/sms/storageRecord")
public class StorageRecordController extends BaseController
{
    private String prefix = "sms/storageRecord";

    @Autowired
    private IStorageRecordService storageRecordService;

    @RequiresPermissions("sms:storageRecord:view")
    @GetMapping()
    public String storageRecord()
    {
        return prefix + "/storageRecord";
    }

    /**
     * 查询仓储记录列表
     */
    @RequiresPermissions("sms:storageRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StorageRecord storageRecord)
    {
        startPage();
        List<StorageRecord> list = storageRecordService.selectStorageRecordList(storageRecord);
        return getDataTable(list);
    }

    /**
     * 导出仓储记录列表
     */
    @RequiresPermissions("sms:storageRecord:export")
    @Log(title = "仓储记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(StorageRecord storageRecord)
    {
        List<StorageRecord> list = storageRecordService.selectStorageRecordList(storageRecord);
        ExcelUtil<StorageRecord> util = new ExcelUtil<StorageRecord>(StorageRecord.class);
        return util.exportExcel(list, "storageRecord");
    }

    /**
     * 新增仓储记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存仓储记录
     */
    @RequiresPermissions("sms:storageRecord:add")
    @Log(title = "仓储记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(StorageRecord storageRecord)
    {
        return toAjax(storageRecordService.insertStorageRecord(storageRecord));
    }

    /**
     * 修改仓储记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        StorageRecord storageRecord = storageRecordService.selectStorageRecordById(id);
        mmap.put("storageRecord", storageRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存仓储记录
     */
    @RequiresPermissions("sms:storageRecord:edit")
    @Log(title = "仓储记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(StorageRecord storageRecord)
    {
        return toAjax(storageRecordService.updateStorageRecord(storageRecord));
    }

    /**
     * 删除仓储记录
     */
    @RequiresPermissions("sms:storageRecord:remove")
    @Log(title = "仓储记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(storageRecordService.deleteStorageRecordByIds(ids));
    }

    @Log(title = "仓储记录导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("sms:storageRecord:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport,String account,String site) throws Exception
    {
        CsvUtil<StorageRecordImpTempVo> util = new CsvUtil<StorageRecordImpTempVo>(StorageRecordImpTempVo.class);
        List<StorageRecordImpTempVo> imps = util.importCvs(file.getInputStream(),0);
        String message = storageRecordService.importStorageRecord(imps, updateSupport,account,site);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("sms:storageRecord:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<StorageRecord> util = new ExcelUtil<StorageRecord>(StorageRecord.class);
        return util.importTemplateExcel("仓储记录");
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
}
