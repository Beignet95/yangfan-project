package com.ruoyi.project.pms.badcommodity.controller;

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
import com.ruoyi.project.pms.badcommodity.domain.BadCommodityRepeat;
import com.ruoyi.project.pms.badcommodity.service.IBadCommodityRepeatService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 不良记录重复数据Controller
 * 
 * @author Beignet
 * @date 2021-02-18
 */
@Controller
@RequestMapping("/pms/badCommodityRepeat")
public class BadCommodityRepeatController extends BaseController
{
    private String prefix = "pms/badCommodityRepeat";

    @Autowired
    private IBadCommodityRepeatService badCommodityRepeatService;

    @RequiresPermissions("pms:badCommodityRepeat:view")
    @GetMapping()
    public String badCommodityRepeat(String repeatId,ModelMap mmap)
    {
        mmap.addAttribute("repeatId",repeatId);
        return prefix + "/badCommodityRepeat";
    }

    /**
     * 查询不良记录重复数据列表
     */
    @RequiresPermissions("pms:badCommodityRepeat:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BadCommodityRepeat badCommodityRepeat)
    {
        startPage();
        List<BadCommodityRepeat> list = badCommodityRepeatService.selectBadCommodityRepeatList(badCommodityRepeat);
        return getDataTable(list);
    }

    /**
     * 导出不良记录重复数据列表
     */
    @RequiresPermissions("pms:badCommodityRepeat:export")
    @Log(title = "不良记录重复数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BadCommodityRepeat badCommodityRepeat)
    {
        List<BadCommodityRepeat> list = badCommodityRepeatService.selectBadCommodityRepeatList(badCommodityRepeat);
        ExcelUtil<BadCommodityRepeat> util = new ExcelUtil<BadCommodityRepeat>(BadCommodityRepeat.class);
        return util.exportExcel(list, "badCommodityRepeat");
    }

    /**
     * 新增不良记录重复数据
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存不良记录重复数据
     */
    @RequiresPermissions("pms:badCommodityRepeat:add")
    @Log(title = "不良记录重复数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BadCommodityRepeat badCommodityRepeat)
    {
        return toAjax(badCommodityRepeatService.insertBadCommodityRepeat(badCommodityRepeat));
    }

    /**
     * 修改不良记录重复数据
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BadCommodityRepeat badCommodityRepeat = badCommodityRepeatService.selectBadCommodityRepeatById(id);
        mmap.put("badCommodityRepeat", badCommodityRepeat);
        return prefix + "/edit";
    }

    /**
     * 修改保存不良记录重复数据
     */
    @RequiresPermissions("pms:badCommodityRepeat:edit")
    @Log(title = "不良记录重复数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BadCommodityRepeat badCommodityRepeat)
    {
        return toAjax(badCommodityRepeatService.updateBadCommodityRepeat(badCommodityRepeat));
    }

    /**
     * 删除不良记录重复数据
     */
    @RequiresPermissions("pms:badCommodityRepeat:remove")
    @Log(title = "不良记录重复数据", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(badCommodityRepeatService.deleteBadCommodityRepeatByIds(ids));
    }

    @Log(title = "不良记录重复数据导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("pms:badCommodityRepeat:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<BadCommodityRepeat> util = new ExcelUtil<BadCommodityRepeat>(BadCommodityRepeat.class);
        List<BadCommodityRepeat> badCommodityRepeatList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = badCommodityRepeatService.importBadCommodityRepeat(badCommodityRepeatList, updateSupport);
        return AjaxResult.success(message);
    }
}
