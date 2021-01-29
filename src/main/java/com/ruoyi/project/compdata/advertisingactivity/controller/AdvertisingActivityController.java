package com.ruoyi.project.compdata.advertisingactivity.controller;

import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.compdata.finance.domain.Finance;
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
import com.ruoyi.project.compdata.advertisingactivity.domain.AdvertisingActivity;
import com.ruoyi.project.compdata.advertisingactivity.service.IAdvertisingActivityService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 广告活动映射Controller
 * 
 * @author Beignet
 * @date 2021-01-26
 */
@Controller
@RequestMapping("/compdata/advertisingactivity")
public class AdvertisingActivityController extends BaseController
{
    private String prefix = "compdata/advertisingactivity";

    @Autowired
    private IAdvertisingActivityService advertisingActivityService;

    @RequiresPermissions("compdata:advertisingactivity:view")
    @GetMapping()
    public String advertisingactivity()
    {
        return prefix + "/advertisingactivity";
    }

    /**
     * 查询广告活动映射列表
     */
    @RequiresPermissions("compdata:advertisingactivity:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AdvertisingActivity advertisingActivity)
    {
        startPage();
        List<AdvertisingActivity> list = advertisingActivityService.selectAdvertisingActivityList(advertisingActivity);
        return getDataTable(list);
    }

    /**
     * 导出广告活动映射列表
     */
    @RequiresPermissions("compdata:advertisingactivity:export")
    @Log(title = "广告活动映射", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AdvertisingActivity advertisingActivity)
    {
        List<AdvertisingActivity> list = advertisingActivityService.selectAdvertisingActivityList(advertisingActivity);
        ExcelUtil<AdvertisingActivity> util = new ExcelUtil<AdvertisingActivity>(AdvertisingActivity.class);
        return util.exportExcel(list, "advertisingactivity");
    }

    /**
     * 新增广告活动映射
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存广告活动映射
     */
    @RequiresPermissions("compdata:advertisingactivity:add")
    @Log(title = "广告活动映射", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AdvertisingActivity advertisingActivity)
    {
        return toAjax(advertisingActivityService.insertAdvertisingActivity(advertisingActivity));
    }

    /**
     * 修改广告活动映射
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AdvertisingActivity advertisingActivity = advertisingActivityService.selectAdvertisingActivityById(id);
        mmap.put("advertisingActivity", advertisingActivity);
        return prefix + "/edit";
    }

    /**
     * 修改保存广告活动映射
     */
    @RequiresPermissions("compdata:advertisingactivity:edit")
    @Log(title = "广告活动映射", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AdvertisingActivity advertisingActivity)
    {
        return toAjax(advertisingActivityService.updateAdvertisingActivity(advertisingActivity));
    }

    /**
     * 删除广告活动映射
     */
    @RequiresPermissions("compdata:advertisingactivity:remove")
    @Log(title = "广告活动映射", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(advertisingActivityService.deleteAdvertisingActivityByIds(ids));
    }

    @Log(title = "广告活动映射数据导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("compdata:advertisingactivity:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<AdvertisingActivity> util = new ExcelUtil<AdvertisingActivity>(AdvertisingActivity.class);
        List<AdvertisingActivity> advertisingActivityList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = advertisingActivityService.importAdvertisingActivity(advertisingActivityList, updateSupport);
        return AjaxResult.success(message);
    }
}
