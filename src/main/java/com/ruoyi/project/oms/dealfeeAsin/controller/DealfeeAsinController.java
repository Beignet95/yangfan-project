package com.ruoyi.project.oms.dealfeeAsin.controller;

import java.util.List;
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
import com.ruoyi.project.oms.dealfeeAsin.domain.DealfeeAsin;
import com.ruoyi.project.oms.dealfeeAsin.service.IDealfeeAsinService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * Deal Fee与ASIN映射Controller
 * 
 * @author Beignet
 * @date 2021-03-16
 */
@Controller
@RequestMapping("/oms/dealfeeAsin")
public class DealfeeAsinController extends BaseController
{
    private String prefix = "oms/dealfeeAsin";

    @Autowired
    private IDealfeeAsinService dealfeeAsinService;

    @RequiresPermissions("oms:dealfeeAsin:view")
    @GetMapping()
    public String dealfeeAsin()
    {
        return prefix + "/dealfeeAsin";
    }

    /**
     * 查询Deal Fee与ASIN映射列表
     */
    @RequiresPermissions("oms:dealfeeAsin:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DealfeeAsin dealfeeAsin)
    {
        startPage();
        List<DealfeeAsin> list = dealfeeAsinService.selectDealfeeAsinList(dealfeeAsin);
        return getDataTable(list);
    }

    /**
     * 导出Deal Fee与ASIN映射列表
     */
    @RequiresPermissions("oms:dealfeeAsin:export")
    @Log(title = "Deal Fee与ASIN映射", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DealfeeAsin dealfeeAsin)
    {
        List<DealfeeAsin> list = dealfeeAsinService.selectDealfeeAsinList(dealfeeAsin);
        ExcelUtil<DealfeeAsin> util = new ExcelUtil<DealfeeAsin>(DealfeeAsin.class);
        return util.exportExcel(list, "dealfeeAsin");
    }

    /**
     * 新增Deal Fee与ASIN映射
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存Deal Fee与ASIN映射
     */
    @RequiresPermissions("oms:dealfeeAsin:add")
    @Log(title = "Deal Fee与ASIN映射", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DealfeeAsin dealfeeAsin)
    {
        return toAjax(dealfeeAsinService.insertDealfeeAsin(dealfeeAsin));
    }

    /**
     * 修改Deal Fee与ASIN映射
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DealfeeAsin dealfeeAsin = dealfeeAsinService.selectDealfeeAsinById(id);
        mmap.put("dealfeeAsin", dealfeeAsin);
        return prefix + "/edit";
    }

    /**
     * 修改保存Deal Fee与ASIN映射
     */
    @RequiresPermissions("oms:dealfeeAsin:edit")
    @Log(title = "Deal Fee与ASIN映射", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DealfeeAsin dealfeeAsin)
    {
        return toAjax(dealfeeAsinService.updateDealfeeAsin(dealfeeAsin));
    }

    /**
     * 删除Deal Fee与ASIN映射
     */
    @RequiresPermissions("oms:dealfeeAsin:remove")
    @Log(title = "Deal Fee与ASIN映射", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(dealfeeAsinService.deleteDealfeeAsinByIds(ids));
    }

    @Log(title = "Deal Fee与ASIN映射导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("oms:dealfeeAsin:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<DealfeeAsin> util = new ExcelUtil<DealfeeAsin>(DealfeeAsin.class);
        List<DealfeeAsin> dealfeeAsinList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = dealfeeAsinService.importDealfeeAsin(dealfeeAsinList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("oms:dealfeeAsin:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<DealfeeAsin> util = new ExcelUtil<DealfeeAsin>(DealfeeAsin.class);
        return util.importTemplateExcel("Deal Fee与ASIN映射");
    }
}
