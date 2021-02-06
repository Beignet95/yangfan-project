package com.ruoyi.project.system.net.zwIpFiter.controller;

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
import com.ruoyi.project.system.net.zwIpFiter.domain.SysZwIpFilter;
import com.ruoyi.project.system.net.zwIpFiter.service.ISysZwIpFilterService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * ZwIpFilterController
 * 
 * @author Beignet
 * @date 2021-01-30
 */
@Controller
@RequestMapping("/zwIpFilter/zwIpFiter")
public class SysZwIpFilterController extends BaseController
{
    private String prefix = "zwIpFilter/zwIpFiter";

    @Autowired
    private ISysZwIpFilterService sysZwIpFilterService;

    @RequiresPermissions("zwIpFilter:zwIpFiter:view")
    @GetMapping()
    public String zwIpFiter()
    {
        return prefix + "/zwIpFiter";
    }

    /**
     * 查询ZwIpFilter列表
     */
    @RequiresPermissions("zwIpFilter:zwIpFiter:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysZwIpFilter sysZwIpFilter)
    {
        startPage();
        List<SysZwIpFilter> list = sysZwIpFilterService.selectSysZwIpFilterList(sysZwIpFilter);
        return getDataTable(list);
    }

    /**
     * 导出ZwIpFilter列表
     */
    @RequiresPermissions("zwIpFilter:zwIpFiter:export")
    @Log(title = "ZwIpFilter", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysZwIpFilter sysZwIpFilter)
    {
        List<SysZwIpFilter> list = sysZwIpFilterService.selectSysZwIpFilterList(sysZwIpFilter);
        ExcelUtil<SysZwIpFilter> util = new ExcelUtil<SysZwIpFilter>(SysZwIpFilter.class);
        return util.exportExcel(list, "zwIpFiter");
    }

    /**
     * 新增ZwIpFilter
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存ZwIpFilter
     */
    @RequiresPermissions("zwIpFilter:zwIpFiter:add")
    @Log(title = "ZwIpFilter", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysZwIpFilter sysZwIpFilter)
    {
        return toAjax(sysZwIpFilterService.insertSysZwIpFilter(sysZwIpFilter));
    }

    /**
     * 修改ZwIpFilter
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SysZwIpFilter sysZwIpFilter = sysZwIpFilterService.selectSysZwIpFilterById(id);
        mmap.put("sysZwIpFilter", sysZwIpFilter);
        return prefix + "/edit";
    }

    /**
     * 修改保存ZwIpFilter
     */
    @RequiresPermissions("zwIpFilter:zwIpFiter:edit")
    @Log(title = "ZwIpFilter", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysZwIpFilter sysZwIpFilter)
    {
        return toAjax(sysZwIpFilterService.updateSysZwIpFilter(sysZwIpFilter));
    }

    /**
     * 删除ZwIpFilter
     */
    @RequiresPermissions("zwIpFilter:zwIpFiter:remove")
    @Log(title = "ZwIpFilter", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysZwIpFilterService.deleteSysZwIpFilterByIds(ids));
    }

    @Log(title = "ZwIpFilter导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("zwIpFilter:zwIpFiter:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysZwIpFilter> util = new ExcelUtil<SysZwIpFilter>(SysZwIpFilter.class);
        List<SysZwIpFilter> zwIpFiterList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = sysZwIpFilterService.importSysZwIpFilter(zwIpFiterList, updateSupport);
        return AjaxResult.success(message);
    }
}
