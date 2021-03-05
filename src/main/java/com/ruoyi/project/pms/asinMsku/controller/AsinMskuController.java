package com.ruoyi.project.pms.asinMsku.controller;

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
import com.ruoyi.project.pms.asinMsku.domain.AsinMsku;
import com.ruoyi.project.pms.asinMsku.service.IAsinMskuService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * ASIN与亚马逊产品SKU关系Controller
 * 
 * @author Beignet
 * @date 2021-03-05
 */
@Controller
@RequestMapping("/pms/asinMsku")
public class AsinMskuController extends BaseController
{
    private String prefix = "pms/asinMsku";

    @Autowired
    private IAsinMskuService asinMskuService;

    @RequiresPermissions("pms:asinMsku:view")
    @GetMapping()
    public String asinMsku()
    {
        return prefix + "/asinMsku";
    }

    /**
     * 查询ASIN与亚马逊产品SKU关系列表
     */
    @RequiresPermissions("pms:asinMsku:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AsinMsku asinMsku)
    {
        startPage();
        List<AsinMsku> list = asinMskuService.selectAsinMskuList(asinMsku);
        return getDataTable(list);
    }

    /**
     * 导出ASIN与亚马逊产品SKU关系列表
     */
    @RequiresPermissions("pms:asinMsku:export")
    @Log(title = "ASIN与亚马逊产品SKU关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AsinMsku asinMsku)
    {
        List<AsinMsku> list = asinMskuService.selectAsinMskuList(asinMsku);
        ExcelUtil<AsinMsku> util = new ExcelUtil<AsinMsku>(AsinMsku.class);
        return util.exportExcel(list, "asinMsku");
    }

    /**
     * 新增ASIN与亚马逊产品SKU关系
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存ASIN与亚马逊产品SKU关系
     */
    @RequiresPermissions("pms:asinMsku:add")
    @Log(title = "ASIN与亚马逊产品SKU关系", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AsinMsku asinMsku)
    {
        return toAjax(asinMskuService.insertAsinMsku(asinMsku));
    }

    /**
     * 修改ASIN与亚马逊产品SKU关系
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AsinMsku asinMsku = asinMskuService.selectAsinMskuById(id);
        mmap.put("asinMsku", asinMsku);
        return prefix + "/edit";
    }

    /**
     * 修改保存ASIN与亚马逊产品SKU关系
     */
    @RequiresPermissions("pms:asinMsku:edit")
    @Log(title = "ASIN与亚马逊产品SKU关系", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AsinMsku asinMsku)
    {
        return toAjax(asinMskuService.updateAsinMsku(asinMsku));
    }

    /**
     * 删除ASIN与亚马逊产品SKU关系
     */
    @RequiresPermissions("pms:asinMsku:remove")
    @Log(title = "ASIN与亚马逊产品SKU关系", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(asinMskuService.deleteAsinMskuByIds(ids));
    }

    @Log(title = "ASIN与亚马逊产品SKU关系导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("pms:asinMsku:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<AsinMsku> util = new ExcelUtil<AsinMsku>(AsinMsku.class);
        List<AsinMsku> asinMskuList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = asinMskuService.importAsinMsku(asinMskuList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("pms:asinMsku:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<AsinMsku> util = new ExcelUtil<AsinMsku>(AsinMsku.class);
        return util.importTemplateExcel("ASIN与亚马逊产品SKU关系");
    }
}
