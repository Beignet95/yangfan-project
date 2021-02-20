package com.ruoyi.project.compdata.displayadvertising.controller;

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
import com.ruoyi.project.compdata.displayadvertising.domain.DisplayAdvertising;
import com.ruoyi.project.compdata.displayadvertising.service.IDisplayAdvertisingService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * Display广告数据源Controller
 * 
 * @author Beignet
 * @date 2021-02-20
 */
@Controller
@RequestMapping("/compdata/displayAdvertising")
public class DisplayAdvertisingController extends BaseController
{
    private String prefix = "compdata/displayAdvertising";

    @Autowired
    private IDisplayAdvertisingService displayAdvertisingService;

    @RequiresPermissions("compdata:displayAdvertising:view")
    @GetMapping()
    public String displayAdvertising()
    {
        return prefix + "/displayAdvertising";
    }

    /**
     * 查询Display广告数据源列表
     */
    @RequiresPermissions("compdata:displayAdvertising:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DisplayAdvertising displayAdvertising)
    {
        startPage();
        List<DisplayAdvertising> list = displayAdvertisingService.selectDisplayAdvertisingList(displayAdvertising);
        return getDataTable(list);
    }

    /**
     * 导出Display广告数据源列表
     */
    @RequiresPermissions("compdata:displayAdvertising:export")
    @Log(title = "Display广告数据源", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DisplayAdvertising displayAdvertising)
    {
        List<DisplayAdvertising> list = displayAdvertisingService.selectDisplayAdvertisingList(displayAdvertising);
        ExcelUtil<DisplayAdvertising> util = new ExcelUtil<DisplayAdvertising>(DisplayAdvertising.class);
        return util.exportExcel(list, "displayAdvertising");
    }

    /**
     * 新增Display广告数据源
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存Display广告数据源
     */
    @RequiresPermissions("compdata:displayAdvertising:add")
    @Log(title = "Display广告数据源", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DisplayAdvertising displayAdvertising)
    {
        return toAjax(displayAdvertisingService.insertDisplayAdvertising(displayAdvertising));
    }

    /**
     * 修改Display广告数据源
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DisplayAdvertising displayAdvertising = displayAdvertisingService.selectDisplayAdvertisingById(id);
        mmap.put("displayAdvertising", displayAdvertising);
        return prefix + "/edit";
    }

    /**
     * 修改保存Display广告数据源
     */
    @RequiresPermissions("compdata:displayAdvertising:edit")
    @Log(title = "Display广告数据源", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DisplayAdvertising displayAdvertising)
    {
        return toAjax(displayAdvertisingService.updateDisplayAdvertising(displayAdvertising));
    }

    /**
     * 删除Display广告数据源
     */
    @RequiresPermissions("compdata:displayAdvertising:remove")
    @Log(title = "Display广告数据源", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(displayAdvertisingService.deleteDisplayAdvertisingByIds(ids));
    }

    @Log(title = "Display广告数据源导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("compdata:displayAdvertising:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<DisplayAdvertising> util = new ExcelUtil<DisplayAdvertising>(DisplayAdvertising.class);
        List<DisplayAdvertising> displayAdvertisingList = util.importExcel(StringUtils.EMPTY,file.getInputStream(),0);
        String message = displayAdvertisingService.importDisplayAdvertising(displayAdvertisingList, updateSupport);
        return AjaxResult.success(message);
    }

    /**
     * 广告分析页面
     */
    @GetMapping("/analysis")
    @RequiresPermissions("compdata:displayAdvertising:analysis")
    public String analysis(ModelMap mmap)
    {
        return prefix + "/analysis";
    }
}
