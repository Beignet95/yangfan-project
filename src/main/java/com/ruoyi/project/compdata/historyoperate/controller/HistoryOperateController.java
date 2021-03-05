package com.ruoyi.project.compdata.historyoperate.controller;

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
import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;
import com.ruoyi.project.compdata.historyoperate.service.IHistoryOperateService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 历史操作记录Controller
 * 
 * @author Beignet
 * @date 2021-02-26
 */
@Controller
@RequestMapping("/sys/historyoperate")
public class HistoryOperateController extends BaseController
{
    private String prefix = "sys/historyoperate";

    @Autowired
    private IHistoryOperateService historyOperateService;

    @RequiresPermissions("sys:historyoperate:view")
    @GetMapping()
    public String historyoperate()
    {
        return prefix + "/historyoperate";
    }

    /**
     * 查询历史操作记录列表
     */
    @RequiresPermissions("sys:historyoperate:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(HistoryOperate historyOperate)
    {
        startPage();
        List<HistoryOperate> list = historyOperateService.selectHistoryOperateList(historyOperate);
        return getDataTable(list);
    }

    /**
     * 导出历史操作记录列表
     */
    @RequiresPermissions("sys:historyoperate:export")
    @Log(title = "历史操作记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(HistoryOperate historyOperate)
    {
        List<HistoryOperate> list = historyOperateService.selectHistoryOperateList(historyOperate);
        ExcelUtil<HistoryOperate> util = new ExcelUtil<HistoryOperate>(HistoryOperate.class);
        return util.exportExcel(list, "historyoperate");
    }

    /**
     * 新增历史操作记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存历史操作记录
     */
    @RequiresPermissions("sys:historyoperate:add")
    @Log(title = "历史操作记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(HistoryOperate historyOperate)
    {
        return toAjax(historyOperateService.insertHistoryOperate(historyOperate));
    }

    /**
     * 修改历史操作记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        HistoryOperate historyOperate = historyOperateService.selectHistoryOperateById(id);
        mmap.put("historyOperate", historyOperate);
        return prefix + "/edit";
    }

    /**
     * 修改保存历史操作记录
     */
    @RequiresPermissions("sys:historyoperate:edit")
    @Log(title = "历史操作记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(HistoryOperate historyOperate)
    {
        return toAjax(historyOperateService.updateHistoryOperate(historyOperate));
    }

    /**
     * 删除历史操作记录
     */
    @RequiresPermissions("sys:historyoperate:remove")
    @Log(title = "历史操作记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(historyOperateService.deleteHistoryOperateByIds(ids));
    }

    @Log(title = "历史操作记录导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("sys:historyoperate:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<HistoryOperate> util = new ExcelUtil<HistoryOperate>(HistoryOperate.class);
        List<HistoryOperate> historyoperateList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = historyOperateService.importHistoryOperate(historyoperateList, updateSupport);
        return AjaxResult.success(message);
    }
}
