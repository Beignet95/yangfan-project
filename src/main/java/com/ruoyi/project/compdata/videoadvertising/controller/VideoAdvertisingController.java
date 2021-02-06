package com.ruoyi.project.compdata.videoadvertising.controller;

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
import com.ruoyi.project.compdata.videoadvertising.domain.VideoAdvertising;
import com.ruoyi.project.compdata.videoadvertising.service.IVideoAdvertisingService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频广告数据源Controller
 * 
 * @author Beignet
 * @date 2021-01-29
 */
@Controller
@RequestMapping("/compdata/videoAdvertising")
public class VideoAdvertisingController extends BaseController
{
    private String prefix = "compdata/videoAdvertising";

    @Autowired
    private IVideoAdvertisingService videoAdvertisingService;

    @RequiresPermissions("compdata:videoAdvertising:view")
    @GetMapping()
    public String videoAdvertising()
    {
        return prefix + "/videoAdvertising";
    }

    /**
     * 查询视频广告数据源列表
     */
    @RequiresPermissions("compdata:videoAdvertising:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(VideoAdvertising videoAdvertising)
    {
        startPage();
        List<VideoAdvertising> list = videoAdvertisingService.selectVideoAdvertisingList(videoAdvertising);
        return getDataTable(list);
    }

    /**
     * 导出视频广告数据源列表
     */
    @RequiresPermissions("compdata:videoAdvertising:export")
    @Log(title = "视频广告数据源", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(VideoAdvertising videoAdvertising)
    {
        List<VideoAdvertising> list = videoAdvertisingService.selectVideoAdvertisingList(videoAdvertising);
        ExcelUtil<VideoAdvertising> util = new ExcelUtil<VideoAdvertising>(VideoAdvertising.class);
        return util.exportExcel(list, "videoAdvertising");
    }

    /**
     * 新增视频广告数据源
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存视频广告数据源
     */
    @RequiresPermissions("compdata:videoAdvertising:add")
    @Log(title = "视频广告数据源", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(VideoAdvertising videoAdvertising)
    {
        return toAjax(videoAdvertisingService.insertVideoAdvertising(videoAdvertising));
    }

    /**
     * 修改视频广告数据源
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        VideoAdvertising videoAdvertising = videoAdvertisingService.selectVideoAdvertisingById(id);
        mmap.put("videoAdvertising", videoAdvertising);
        return prefix + "/edit";
    }

    /**
     * 修改保存视频广告数据源
     */
    @RequiresPermissions("compdata:videoAdvertising:edit")
    @Log(title = "视频广告数据源", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(VideoAdvertising videoAdvertising)
    {
        return toAjax(videoAdvertisingService.updateVideoAdvertising(videoAdvertising));
    }

    /**
     * 删除视频广告数据源
     */
    @RequiresPermissions("compdata:videoAdvertising:remove")
    @Log(title = "视频广告数据源", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(videoAdvertisingService.deleteVideoAdvertisingByIds(ids));
    }

    @Log(title = "视频广告数据源导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("compdata:videoAdvertising:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<VideoAdvertising> util = new ExcelUtil<VideoAdvertising>(VideoAdvertising.class);
        List<VideoAdvertising> videoAdvertisingList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = videoAdvertisingService.importVideoAdvertising(videoAdvertisingList, updateSupport);
        return AjaxResult.success(message);
    }

    /**
     * 广告分析页面
     */
    @GetMapping("/analysis")
    @RequiresPermissions("compdata:videoAdvertising:analysis")
    public String analysis(ModelMap mmap)
    {
        return prefix + "/analysis";
    }
}
