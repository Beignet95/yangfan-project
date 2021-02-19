package com.ruoyi.project.compdata.advertising.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.compdata.advertising.domain.Advertising;
import com.ruoyi.project.compdata.advertising.service.IAdvertisingService;
import com.ruoyi.project.compdata.advertising.vo.AdvertisingAnalyParamVo;
import com.ruoyi.project.compdata.advertising.vo.AdvertisingAnalySearchVo;
import com.ruoyi.project.compdata.advertising.vo.AdvertisingEchartsVo;
import com.ruoyi.project.compdata.advertising.vo.AdvertisingTempVo;
import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.system.user.domain.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 【请填写功能名称】Controller
 * 
 * @author Beignet
 * @date 2021-01-08
 */
@Controller
@RequestMapping("/compdata/advertising")
public class AdvertisingController extends BaseController
{
    private String prefix = "compdata/advertising";

    @Autowired
    private IAdvertisingService advertisingService;

    @RequiresPermissions("compdata:advertising:view")
    @GetMapping()
    public String advertising(ModelMap mmap)
    {
        AdvertisingAnalyParamVo advertisingAnalyParamVo = new AdvertisingAnalyParamVo();
        AdvertisingAnalySearchVo advertisingAnalySearchVo = advertisingService.selectAdvertisingAnalySearchVo(advertisingAnalyParamVo);
        mmap.put("searchMap",advertisingAnalySearchVo);
        return prefix + "/advertising";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("compdata:advertising:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Advertising advertising)
    {
        startPage();
        List<Advertising> list = advertisingService.selectAdvertisingList(advertising);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("compdata:advertising:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Advertising advertising)
    {
        List<Advertising> list = advertisingService.selectAdvertisingList(advertising);
        ExcelUtil<Advertising> util = new ExcelUtil<Advertising>(Advertising.class);
        return util.exportExcel(list, "advertising");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("compdata:advertising:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Advertising advertising)
    {
        return toAjax(advertisingService.insertAdvertising(advertising));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Advertising advertising = advertisingService.selectAdvertisingById(id);
        mmap.put("advertising", advertising);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("compdata:advertising:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Advertising advertising)
    {
        return toAjax(advertisingService.updateAdvertising(advertising));
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("compdata:advertising:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(advertisingService.deleteAdvertisingByIds(ids));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/getAdvertisingAnalySearch")
    @RequiresPermissions("compdata:advertising:analysis")
    @ResponseBody
    public AjaxResult getAdvertisingAnalySearch(AdvertisingAnalyParamVo advertisingAnalyParamVo, ModelMap mmap)
    {
          AdvertisingAnalySearchVo advertisingAnalySearchVo = advertisingService.selectAdvertisingAnalySearchVo(advertisingAnalyParamVo);
          return AjaxResult.success(advertisingAnalySearchVo);
    }

    /**
     * 广告分析页面
     */
    @GetMapping("/analysis")
    @RequiresPermissions("compdata:advertising:analysis")
    public String analysis(ModelMap mmap)
    {
        return prefix + "/analysis";
    }

    @Log(title = "广告数据导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("compdata:advertising:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Advertising> util = new ExcelUtil<Advertising>(Advertising.class);
        List<Advertising> AdvertisingList = util.importExcel(StringUtils.EMPTY,file.getInputStream(),0);
        String message = advertisingService.importData(AdvertisingList, updateSupport);
        return AjaxResult.success(message);
    }

    /**
     * 获取广告分析数据
     * @param advertisingAnalyParamVo
     * @param mmap
     * @return
     */
    @GetMapping("/getAnalysisData")
    @RequiresPermissions("compdata:advertising:analysis")
    @ResponseBody
    public AjaxResult getAnalysisData(AdvertisingAnalyParamVo advertisingAnalyParamVo, ModelMap mmap)
    {
        AdvertisingEchartsVo advertisingEchartsVo = advertisingService.selectAdvertisingEchartsVo(advertisingAnalyParamVo);
        return AjaxResult.success(advertisingEchartsVo);
    }

    //导出 导入模板
    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<AdvertisingTempVo> util = new ExcelUtil<AdvertisingTempVo>(AdvertisingTempVo.class);
        return util.importTemplateExcel("广告源数据");
    }

}
