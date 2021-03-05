package com.ruoyi.project.pms.productinfoReation.controller;

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
import com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation;
import com.ruoyi.project.pms.productinfoReation.service.IProductinfoRelationService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 产品信息映射Controller
 * 
 * @author Beignet
 * @date 2021-03-05
 */
@Controller
@RequestMapping("/pms/productinfoReation")
public class ProductinfoRelationController extends BaseController
{
    private String prefix = "pms/productinfoReation";

    @Autowired
    private IProductinfoRelationService productinfoRelationService;

    @RequiresPermissions("pms:productinfoReation:view")
    @GetMapping()
    public String productinfoReation()
    {
        return prefix + "/productinfoReation";
    }

    /**
     * 查询产品信息映射列表
     */
    @RequiresPermissions("pms:productinfoReation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProductinfoRelation productinfoRelation)
    {
        startPage();
        List<ProductinfoRelation> list = productinfoRelationService.selectProductinfoRelationList(productinfoRelation);
        return getDataTable(list);
    }

    /**
     * 导出产品信息映射列表
     */
    @RequiresPermissions("pms:productinfoReation:export")
    @Log(title = "产品信息映射", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProductinfoRelation productinfoRelation)
    {
        List<ProductinfoRelation> list = productinfoRelationService.selectProductinfoRelationList(productinfoRelation);
        ExcelUtil<ProductinfoRelation> util = new ExcelUtil<ProductinfoRelation>(ProductinfoRelation.class);
        return util.exportExcel(list, "productinfoReation");
    }

    /**
     * 新增产品信息映射
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存产品信息映射
     */
    @RequiresPermissions("pms:productinfoReation:add")
    @Log(title = "产品信息映射", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProductinfoRelation productinfoRelation)
    {
        return toAjax(productinfoRelationService.insertProductinfoRelation(productinfoRelation));
    }

    /**
     * 修改产品信息映射
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ProductinfoRelation productinfoRelation = productinfoRelationService.selectProductinfoRelationById(id);
        mmap.put("productinfoRelation", productinfoRelation);
        return prefix + "/edit";
    }

    /**
     * 修改保存产品信息映射
     */
    @RequiresPermissions("pms:productinfoReation:edit")
    @Log(title = "产品信息映射", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProductinfoRelation productinfoRelation)
    {
        return toAjax(productinfoRelationService.updateProductinfoRelation(productinfoRelation));
    }

    /**
     * 删除产品信息映射
     */
    @RequiresPermissions("pms:productinfoReation:remove")
    @Log(title = "产品信息映射", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(productinfoRelationService.deleteProductinfoRelationByIds(ids));
    }

    @Log(title = "产品信息映射导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("pms:productinfoReation:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<ProductinfoRelation> util = new ExcelUtil<ProductinfoRelation>(ProductinfoRelation.class);
        List<ProductinfoRelation> productinfoReationList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = productinfoRelationService.importProductinfoRelation(productinfoReationList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("pms:productinfoReation:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<ProductinfoRelation> util = new ExcelUtil<ProductinfoRelation>(ProductinfoRelation.class);
        return util.importTemplateExcel("产品信息映射");
    }
}
