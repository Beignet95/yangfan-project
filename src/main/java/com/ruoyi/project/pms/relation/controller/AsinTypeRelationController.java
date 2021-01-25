package com.ruoyi.project.pms.relation.controller;

import java.util.List;

import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.project.pms.relation.domain.AsinTypeRelation;
import com.ruoyi.project.pms.relation.service.IAsinTypeRelationService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * ASIN与型号关联Controller
 * 
 * @author Beignet
 * @date 2021-01-22
 */
@Controller
@RequestMapping("/pms/relation")
public class AsinTypeRelationController extends BaseController
{
    private String prefix = "pms/relation";

    @Autowired
    private IAsinTypeRelationService asinTypeRelationService;

    @RequiresPermissions("pms:relation:view")
    @GetMapping()
    public String relation()
    {
        return prefix + "/relation";
    }

    /**
     * 查询ASIN与型号关联列表
     */
    @RequiresPermissions("pms:relation:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AsinTypeRelation asinTypeRelation)
    {
        startPage();
        List<AsinTypeRelation> list = asinTypeRelationService.selectAsinTypeRelationList(asinTypeRelation);
        return getDataTable(list);
    }

    /**
     * 导出ASIN与型号关联列表
     */
    @RequiresPermissions("pms:relation:export")
    @Log(title = "ASIN与型号关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AsinTypeRelation asinTypeRelation)
    {
        List<AsinTypeRelation> list = asinTypeRelationService.selectAsinTypeRelationList(asinTypeRelation);
        ExcelUtil<AsinTypeRelation> util = new ExcelUtil<AsinTypeRelation>(AsinTypeRelation.class);
        return util.exportExcel(list, "relation");
    }

    /**
     * 新增ASIN与型号关联
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存ASIN与型号关联
     */
    @RequiresPermissions("pms:relation:add")
    @Log(title = "ASIN与型号关联", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AsinTypeRelation asinTypeRelation)
    {
        return toAjax(asinTypeRelationService.insertAsinTypeRelation(asinTypeRelation));
    }

    /**
     * 修改ASIN与型号关联
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AsinTypeRelation asinTypeRelation = asinTypeRelationService.selectAsinTypeRelationById(id);
        mmap.put("asinTypeRelation", asinTypeRelation);
        return prefix + "/edit";
    }

    /**
     * 修改保存ASIN与型号关联
     */
    @RequiresPermissions("pms:relation:edit")
    @Log(title = "ASIN与型号关联", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AsinTypeRelation asinTypeRelation)
    {
        return toAjax(asinTypeRelationService.updateAsinTypeRelation(asinTypeRelation));
    }

    /**
     * 删除ASIN与型号关联
     */
    @RequiresPermissions("pms:relation:remove")
    @Log(title = "ASIN与型号关联", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(asinTypeRelationService.deleteAsinTypeRelationByIds(ids));
    }

    @Log(title = "ASIN与型号关联数据导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("pms:relation:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<AsinTypeRelation> util = new ExcelUtil<AsinTypeRelation>(AsinTypeRelation.class);
        List<AsinTypeRelation> relationList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = asinTypeRelationService.importAsinTypeRelation(relationList, updateSupport);
        return AjaxResult.success(message);
    }


    @Log(title = "ASIN与型号关联数据同步", businessType = BusinessType.IMPORT)
    @RequiresPermissions("pms:relation:syncAsinTypeRalation2AdvertisingData")
    @PostMapping("/syncAsinTypeRalation2AdvertisingData")
    @ResponseBody
    public AjaxResult syncAsinTypeRalation2AdvertisingData() throws Exception
    {
        String message = asinTypeRelationService.syncAsinTypeRalation2AdvertisingData();;
        return AjaxResult.success(message);
    }
}
