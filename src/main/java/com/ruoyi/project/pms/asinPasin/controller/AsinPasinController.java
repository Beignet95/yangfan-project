package com.ruoyi.project.pms.asinPasin.controller;

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
import com.ruoyi.project.pms.asinPasin.domain.AsinPasin;
import com.ruoyi.project.pms.asinPasin.service.IAsinPasinService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * ASIN与父ASIN关系Controller
 * 
 * @author Beignet
 * @date 2021-03-08
 */
@Controller
@RequestMapping("/pms/asinPasin")
public class AsinPasinController extends BaseController
{
    private String prefix = "pms/asinPasin";

    private final int noImportRowNum = 0;

    @Autowired
    private IAsinPasinService asinPasinService;

    @RequiresPermissions("pms:asinPasin:view")
    @GetMapping()
    public String asinPasin()
    {
        return prefix + "/asinPasin";
    }

    /**
     * 查询ASIN与父ASIN关系列表
     */
    @RequiresPermissions("pms:asinPasin:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AsinPasin asinPasin)
    {
        startPage();
        List<AsinPasin> list = asinPasinService.selectAsinPasinList(asinPasin);
        return getDataTable(list);
    }

    /**
     * 导出ASIN与父ASIN关系列表
     */
    @RequiresPermissions("pms:asinPasin:export")
    @Log(title = "ASIN与父ASIN关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AsinPasin asinPasin)
    {
        List<AsinPasin> list = asinPasinService.selectAsinPasinList(asinPasin);
        ExcelUtil<AsinPasin> util = new ExcelUtil<AsinPasin>(AsinPasin.class);
        return util.exportExcel(list, "asinPasin");
    }

    /**
     * 新增ASIN与父ASIN关系
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存ASIN与父ASIN关系
     */
    @RequiresPermissions("pms:asinPasin:add")
    @Log(title = "ASIN与父ASIN关系", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AsinPasin asinPasin)
    {
        return toAjax(asinPasinService.insertAsinPasin(asinPasin));
    }

    /**
     * 修改ASIN与父ASIN关系
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AsinPasin asinPasin = asinPasinService.selectAsinPasinById(id);
        mmap.put("asinPasin", asinPasin);
        return prefix + "/edit";
    }

    /**
     * 修改保存ASIN与父ASIN关系
     */
    @RequiresPermissions("pms:asinPasin:edit")
    @Log(title = "ASIN与父ASIN关系", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AsinPasin asinPasin)
    {
        return toAjax(asinPasinService.updateAsinPasin(asinPasin));
    }

    /**
     * 删除ASIN与父ASIN关系
     */
    @RequiresPermissions("pms:asinPasin:remove")
    @Log(title = "ASIN与父ASIN关系", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(asinPasinService.deleteAsinPasinByIds(ids));
    }

    @Log(title = "ASIN与父ASIN关系导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("pms:asinPasin:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<AsinPasin> util = new ExcelUtil<AsinPasin>(AsinPasin.class);
        List<AsinPasin> asinPasinList = util.importExcel(StringUtils.EMPTY,file.getInputStream(),noImportRowNum);
        String message = asinPasinService.importAsinPasin(asinPasinList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("pms:asinPasin:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<AsinPasin> util = new ExcelUtil<AsinPasin>(AsinPasin.class);
        return util.importTemplateExcel("ASIN与父ASIN关系",noImportRowNum);
    }
}
