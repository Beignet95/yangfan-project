package com.ruoyi.project.ums.site.controller;

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
import com.ruoyi.project.ums.site.domain.Site;
import com.ruoyi.project.ums.site.service.ISiteService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 站点Controller
 * 
 * @author Beignet
 * @date 2021-03-20
 */
@Controller
@RequestMapping("/ums/site")
public class SiteController extends BaseController
{
    private String prefix = "ums/site";

    @Autowired
    private ISiteService siteService;

    @RequiresPermissions("ums:site:view")
    @GetMapping()
    public String site()
    {
        return prefix + "/site";
    }

    /**
     * 查询站点列表
     */
    @RequiresPermissions("ums:site:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Site site)
    {
        startPage();
        List<Site> list = siteService.selectSiteList(site);
        return getDataTable(list);
    }

    /**
     * 导出站点列表
     */
    @RequiresPermissions("ums:site:export")
    @Log(title = "站点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Site site)
    {
        List<Site> list = siteService.selectSiteList(site);
        ExcelUtil<Site> util = new ExcelUtil<Site>(Site.class);
        return util.exportExcel(list, "site");
    }

    /**
     * 新增站点
     */
    @GetMapping("/add")
    public String add(String accountId,ModelMap mmap)
    {
        mmap.addAttribute("accountId",accountId);
        return prefix + "/add";
    }

    /**
     * 新增保存站点
     */
    @RequiresPermissions("ums:site:add")
    @Log(title = "站点", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Site site)
    {
        return toAjax(siteService.insertSite(site));
    }

    /**
     * 修改站点
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Site site = siteService.selectSiteById(id);
        mmap.put("site", site);
        return prefix + "/edit";
    }

    /**
     * 修改保存站点
     */
    @RequiresPermissions("ums:site:edit")
    @Log(title = "站点", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Site site)
    {
        return toAjax(siteService.updateSite(site));
    }

    /**
     * 删除站点
     */
    @RequiresPermissions("ums:site:remove")
    @Log(title = "站点", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(siteService.deleteSiteByIds(ids));
    }

    @Log(title = "站点导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("ums:site:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Site> util = new ExcelUtil<Site>(Site.class);
        List<Site> siteList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = siteService.importSite(siteList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("ums:site:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<Site> util = new ExcelUtil<Site>(Site.class);
        return util.importTemplateExcel("站点");
    }
}
