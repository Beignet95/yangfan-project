package com.ruoyi.project.oms.removalDetail.controller;

import java.util.List;

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
import com.ruoyi.project.oms.removalDetail.domain.RemovalDetail;
import com.ruoyi.project.oms.removalDetail.service.IRemovalDetailService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 移除明细Controller
 * 
 * @author Beignet
 * @date 2021-03-08
 */
@Controller
@RequestMapping("/oms/removalDetail")
public class RemovalDetailController extends BaseController
{
    private String prefix = "oms/removalDetail";

    @Autowired
    private IRemovalDetailService removalDetailService;

    @RequiresPermissions("oms:removalDetail:view")
    @GetMapping()
    public String removalDetail()
    {
        return prefix + "/removalDetail";
    }

    /**
     * 查询移除明细列表
     */
    @RequiresPermissions("oms:removalDetail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RemovalDetail removalDetail)
    {
        startPage();
        List<RemovalDetail> list = removalDetailService.selectRemovalDetailList(removalDetail);
        return getDataTable(list);
    }

    /**
     * 导出移除明细列表
     */
    @RequiresPermissions("oms:removalDetail:export")
    @Log(title = "移除明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RemovalDetail removalDetail)
    {
        List<RemovalDetail> list = removalDetailService.selectRemovalDetailList(removalDetail);
        ExcelUtil<RemovalDetail> util = new ExcelUtil<RemovalDetail>(RemovalDetail.class);
        return util.exportExcel(list, "removalDetail");
    }

    /**
     * 新增移除明细
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存移除明细
     */
    @RequiresPermissions("oms:removalDetail:add")
    @Log(title = "移除明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RemovalDetail removalDetail)
    {
        return toAjax(removalDetailService.insertRemovalDetail(removalDetail));
    }

    /**
     * 修改移除明细
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RemovalDetail removalDetail = removalDetailService.selectRemovalDetailById(id);
        mmap.put("removalDetail", removalDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存移除明细
     */
    @RequiresPermissions("oms:removalDetail:edit")
    @Log(title = "移除明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RemovalDetail removalDetail)
    {
        return toAjax(removalDetailService.updateRemovalDetail(removalDetail));
    }

    /**
     * 删除移除明细
     */
    @RequiresPermissions("oms:removalDetail:remove")
    @Log(title = "移除明细", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(removalDetailService.deleteRemovalDetailByIds(ids));
    }

    @Log(title = "移除明细导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("oms:removalDetail:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        CsvUtil<RemovalDetail> util = new CsvUtil<RemovalDetail>(RemovalDetail.class);
        List<RemovalDetail> removalDetailList = util.importCvs(file.getInputStream(),0);
        String message = removalDetailService.importRemovalDetail(removalDetailList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("oms:removalDetail:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<RemovalDetail> util = new ExcelUtil<RemovalDetail>(RemovalDetail.class);
        return util.importTemplateExcel("移除明细");
    }
}
