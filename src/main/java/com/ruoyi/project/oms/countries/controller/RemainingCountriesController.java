package com.ruoyi.project.oms.countries.controller;

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
import com.ruoyi.project.oms.countries.domain.RemainingCountries;
import com.ruoyi.project.oms.countries.service.IRemainingCountriesService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 其余四国补发Controller
 * 
 * @author Kwl
 * @date 2021-04-01
 */
@Controller
@RequestMapping("/oms/countries")
public class RemainingCountriesController extends BaseController
{
    private String prefix = "oms/countries";

    @Autowired
    private IRemainingCountriesService remainingCountriesService;

    @RequiresPermissions("remaining:countries:view")
    @GetMapping()
    public String countries()
    {
        return prefix + "/countries";
    }

    /**
     * 查询其余四国补发列表
     */
    @RequiresPermissions("remaining:countries:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RemainingCountries remainingCountries)
    {
        startPage();
        List<RemainingCountries> list = remainingCountriesService.selectRemainingCountriesList(remainingCountries);
        return getDataTable(list);
    }

    /**
     * 导出其余四国补发列表
     */
    @RequiresPermissions("remaining:countries:export")
    @Log(title = "其余四国补发", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RemainingCountries remainingCountries)
    {
        List<RemainingCountries> list = remainingCountriesService.selectRemainingCountriesList(remainingCountries);
        ExcelUtil<RemainingCountries> util = new ExcelUtil<RemainingCountries>(RemainingCountries.class);
        return util.exportExcel(list, "countries");
    }

    /**
     * 新增其余四国补发
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存其余四国补发
     */
    @RequiresPermissions("remaining:countries:add")
    @Log(title = "其余四国补发", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RemainingCountries remainingCountries)
    {
        return toAjax(remainingCountriesService.insertRemainingCountries(remainingCountries));
    }

    /**
     * 修改其余四国补发
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RemainingCountries remainingCountries = remainingCountriesService.selectRemainingCountriesById(id);
        mmap.put("remainingCountries", remainingCountries);
        return prefix + "/edit";
    }

    /**
     * 修改保存其余四国补发
     */
    @RequiresPermissions("remaining:countries:edit")
    @Log(title = "其余四国补发", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RemainingCountries remainingCountries)
    {
        return toAjax(remainingCountriesService.updateRemainingCountries(remainingCountries));
    }

    /**
     * 删除其余四国补发
     */
    @RequiresPermissions("remaining:countries:remove")
    @Log(title = "其余四国补发", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(remainingCountriesService.deleteRemainingCountriesByIds(ids));
    }

    @Log(title = "其余四国补发导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("remaining:countries:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<RemainingCountries> util = new ExcelUtil<RemainingCountries>(RemainingCountries.class);
        List<RemainingCountries> countriesList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = remainingCountriesService.importRemainingCountries(countriesList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("remaining:countries:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<RemainingCountries> util = new ExcelUtil<RemainingCountries>(RemainingCountries.class);
        return util.importTemplateExcel("其余四国补发");
    }
}
