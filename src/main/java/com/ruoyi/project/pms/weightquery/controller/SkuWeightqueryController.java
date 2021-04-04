package com.ruoyi.project.pms.weightquery.controller;

import java.util.List;

import com.ruoyi.project.pms.weightquery.domain.SkuWeightquery;
import com.ruoyi.project.pms.weightquery.service.ISkuWeightqueryService;
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
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 补发登记SKUController
 * 
 * @author Beignet
 * @date 2021-04-01
 */
@Controller
@RequestMapping("/pms/weightquery")
public class SkuWeightqueryController extends BaseController
{
    private String prefix = "pms/weightquery";

    @Autowired
    private ISkuWeightqueryService skuWeightqueryService;

    @RequiresPermissions("compdata:weightquery:view")
    @GetMapping()
    public String weightquery()
    {
        return prefix + "/weightquery";
    }

    /**
     * 查询补发登记SKU列表
     */
    @RequiresPermissions("compdata:weightquery:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SkuWeightquery skuWeightquery)
    {
        startPage();
        List<SkuWeightquery> list = skuWeightqueryService.selectSkuWeightqueryList(skuWeightquery);
        return getDataTable(list);
    }

    /**
     * 导出补发登记SKU列表
     */
    @RequiresPermissions("compdata:weightquery:export")
    @Log(title = "补发登记SKU", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SkuWeightquery skuWeightquery)
    {
        List<SkuWeightquery> list = skuWeightqueryService.selectSkuWeightqueryList(skuWeightquery);
        ExcelUtil<SkuWeightquery> util = new ExcelUtil<SkuWeightquery>(SkuWeightquery.class);
        return util.exportExcel(list, "weightquery");
    }

    /**
     * 新增补发登记SKU
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存补发登记SKU
     */
    @RequiresPermissions("compdata:weightquery:add")
    @Log(title = "补发登记SKU", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SkuWeightquery skuWeightquery)
    {
        return toAjax(skuWeightqueryService.insertSkuWeightquery(skuWeightquery));
    }

    /**
     * 修改补发登记SKU
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SkuWeightquery skuWeightquery = skuWeightqueryService.selectSkuWeightqueryById(id);
        mmap.put("skuWeightquery", skuWeightquery);
        return prefix + "/edit";
    }

    /**
     * 修改保存补发登记SKU
     */
    @RequiresPermissions("compdata:weightquery:edit")
    @Log(title = "补发登记SKU", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SkuWeightquery skuWeightquery)
    {
        return toAjax(skuWeightqueryService.updateSkuWeightquery(skuWeightquery));
    }

    /**
     * 删除补发登记SKU
     */
    @RequiresPermissions("compdata:weightquery:remove")
    @Log(title = "补发登记SKU", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(skuWeightqueryService.deleteSkuWeightqueryByIds(ids));
    }

    @Log(title = "补发登记SKU导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("compdata:weightquery:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SkuWeightquery> util = new ExcelUtil<SkuWeightquery>(SkuWeightquery.class);
        List<SkuWeightquery> weightqueryList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = skuWeightqueryService.importSkuWeightquery(weightqueryList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("compdata:weightquery:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<SkuWeightquery> util = new ExcelUtil<SkuWeightquery>(SkuWeightquery.class);
        return util.importTemplateExcel("补发登记SKU");
    }
}
