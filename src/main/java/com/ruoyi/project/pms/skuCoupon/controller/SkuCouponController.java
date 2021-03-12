package com.ruoyi.project.pms.skuCoupon.controller;

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
import com.ruoyi.project.pms.skuCoupon.domain.SkuCoupon;
import com.ruoyi.project.pms.skuCoupon.service.ISkuCouponService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * sku与手续费关系Controller
 * 
 * @author Beignet
 * @date 2021-03-10
 */
@Controller
@RequestMapping("/pms/skuCoupon")
public class SkuCouponController extends BaseController
{
    private String prefix = "pms/skuCoupon";

    @Autowired
    private ISkuCouponService skuCouponService;

    @RequiresPermissions("pms:skuCoupon:view")
    @GetMapping()
    public String skuCoupon()
    {
        return prefix + "/skuCoupon";
    }

    /**
     * 查询sku与手续费关系列表
     */
    @RequiresPermissions("pms:skuCoupon:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SkuCoupon skuCoupon)
    {
        startPage();
        List<SkuCoupon> list = skuCouponService.selectSkuCouponList(skuCoupon);
        return getDataTable(list);
    }

    /**
     * 导出sku与手续费关系列表
     */
    @RequiresPermissions("pms:skuCoupon:export")
    @Log(title = "sku与手续费关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SkuCoupon skuCoupon)
    {
        List<SkuCoupon> list = skuCouponService.selectSkuCouponList(skuCoupon);
        ExcelUtil<SkuCoupon> util = new ExcelUtil<SkuCoupon>(SkuCoupon.class);
        return util.exportExcel(list, "skuCoupon");
    }

    /**
     * 新增sku与手续费关系
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存sku与手续费关系
     */
    @RequiresPermissions("pms:skuCoupon:add")
    @Log(title = "sku与手续费关系", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SkuCoupon skuCoupon)
    {
        return toAjax(skuCouponService.insertSkuCoupon(skuCoupon));
    }

    /**
     * 修改sku与手续费关系
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SkuCoupon skuCoupon = skuCouponService.selectSkuCouponById(id);
        mmap.put("skuCoupon", skuCoupon);
        return prefix + "/edit";
    }

    /**
     * 修改保存sku与手续费关系
     */
    @RequiresPermissions("pms:skuCoupon:edit")
    @Log(title = "sku与手续费关系", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SkuCoupon skuCoupon)
    {
        return toAjax(skuCouponService.updateSkuCoupon(skuCoupon));
    }

    /**
     * 删除sku与手续费关系
     */
    @RequiresPermissions("pms:skuCoupon:remove")
    @Log(title = "sku与手续费关系", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(skuCouponService.deleteSkuCouponByIds(ids));
    }

    @Log(title = "sku与手续费关系导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("pms:skuCoupon:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SkuCoupon> util = new ExcelUtil<SkuCoupon>(SkuCoupon.class);
        List<SkuCoupon> skuCouponList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = skuCouponService.importSkuCoupon(skuCouponList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("pms:skuCoupon:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<SkuCoupon> util = new ExcelUtil<SkuCoupon>(SkuCoupon.class);
        return util.importTemplateExcel("sku与手续费关系");
    }
}
