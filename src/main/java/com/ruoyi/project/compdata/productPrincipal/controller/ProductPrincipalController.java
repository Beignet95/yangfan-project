package com.ruoyi.project.compdata.productPrincipal.controller;

import java.util.List;

import com.ruoyi.project.compdata.advertising.vo.AdvertisingTempVo;
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
import com.ruoyi.project.compdata.productPrincipal.domain.ProductPrincipal;
import com.ruoyi.project.compdata.productPrincipal.service.IProductPrincipalService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 产品负责人映射Controller
 * 
 * @author Beignet
 * @date 2021-02-23
 */
@Controller
@RequestMapping("/compdata/productPrincipal")
public class ProductPrincipalController extends BaseController
{
    private String prefix = "compdata/productPrincipal";

    @Autowired
    private IProductPrincipalService productPrincipalService;

    @RequiresPermissions("compdata:productPrincipal:view")
    @GetMapping()
    public String productPrincipal()
    {
        return prefix + "/productPrincipal";
    }

    /**
     * 查询产品负责人映射列表
     */
    @RequiresPermissions("compdata:productPrincipal:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProductPrincipal productPrincipal)
    {
        startPage();
        List<ProductPrincipal> list = productPrincipalService.selectProductPrincipalList(productPrincipal);
        return getDataTable(list);
    }

    /**
     * 导出产品负责人映射列表
     */
    @RequiresPermissions("compdata:productPrincipal:export")
    @Log(title = "产品负责人映射", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProductPrincipal productPrincipal)
    {
        List<ProductPrincipal> list = productPrincipalService.selectProductPrincipalList(productPrincipal);
        ExcelUtil<ProductPrincipal> util = new ExcelUtil<ProductPrincipal>(ProductPrincipal.class);
        return util.exportExcel(list, "productPrincipal");
    }

    /**
     * 新增产品负责人映射
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存产品负责人映射
     */
    @RequiresPermissions("compdata:productPrincipal:add")
    @Log(title = "产品负责人映射", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProductPrincipal productPrincipal)
    {
        return toAjax(productPrincipalService.insertProductPrincipal(productPrincipal));
    }

    /**
     * 修改产品负责人映射
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ProductPrincipal productPrincipal = productPrincipalService.selectProductPrincipalById(id);
        mmap.put("productPrincipal", productPrincipal);
        return prefix + "/edit";
    }

    /**
     * 修改保存产品负责人映射
     */
    @RequiresPermissions("compdata:productPrincipal:edit")
    @Log(title = "产品负责人映射", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProductPrincipal productPrincipal)
    {
        return toAjax(productPrincipalService.updateProductPrincipal(productPrincipal));
    }

    /**
     * 删除产品负责人映射
     */
    @RequiresPermissions("compdata:productPrincipal:remove")
    @Log(title = "产品负责人映射", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(productPrincipalService.deleteProductPrincipalByIds(ids));
    }

    @Log(title = "产品负责人映射导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("compdata:productPrincipal:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<ProductPrincipal> util = new ExcelUtil<ProductPrincipal>(ProductPrincipal.class);
        List<ProductPrincipal> productPrincipalList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = productPrincipalService.importProductPrincipal(productPrincipalList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("compdata:productPrincipal:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<ProductPrincipal> util = new ExcelUtil<ProductPrincipal>(ProductPrincipal.class);
        return util.importTemplateExcel("sku关系表");
    }
}
