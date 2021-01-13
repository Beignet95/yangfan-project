package com.ruoyi.project.pms.controller;

import java.util.List;

import com.ruoyi.project.pms.service.IPmsSkuInfoService;
import com.ruoyi.project.system.user.domain.User;
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
import com.ruoyi.project.pms.domain.PmsSkuInfo;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * sku信息Controller
 * 
 * @author ruoyi
 * @date 2021-01-07
 */
@Controller
@RequestMapping("/pms/skuinfo")
public class PmsSkuInfoController extends BaseController
{
    private String prefix = "pms/skuinfo";

    @Autowired
    private IPmsSkuInfoService pmsSkuInfoService;

    @RequiresPermissions("pms:skuinfo:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

    /**
     * 查询sku信息列表
     */
    @RequiresPermissions("pms:skuinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PmsSkuInfo pmsSkuInfo)
    {
        startPage();
        List<PmsSkuInfo> list = pmsSkuInfoService.selectPmsSkuInfoList(pmsSkuInfo);
        return getDataTable(list);
    }

    /**
     * 导出sku信息列表
     */
    @RequiresPermissions("pms:skuinfo:export")
    @Log(title = "sku信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PmsSkuInfo pmsSkuInfo)
    {
        List<PmsSkuInfo> list = pmsSkuInfoService.selectPmsSkuInfoList(pmsSkuInfo);
        ExcelUtil<PmsSkuInfo> util = new ExcelUtil<PmsSkuInfo>(PmsSkuInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 新增sku信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存sku信息
     */
    @RequiresPermissions("pms:skuinfo:add")
    @Log(title = "sku信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PmsSkuInfo pmsSkuInfo)
    {
        return toAjax(pmsSkuInfoService.insertPmsSkuInfo(pmsSkuInfo));
    }

    /**
     * 修改sku信息
     */
    @GetMapping("/edit/{skuId}")
    public String edit(@PathVariable("skuId") Long skuId, ModelMap mmap)
    {
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoService.selectPmsSkuInfoById(skuId);
        mmap.put("pmsSkuInfo", pmsSkuInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存sku信息
     */
    @RequiresPermissions("pms:skuinfo:edit")
    @Log(title = "sku信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PmsSkuInfo pmsSkuInfo)
    {
        return toAjax(pmsSkuInfoService.updatePmsSkuInfo(pmsSkuInfo));
    }

    /**
     * 删除sku信息
     */
    @RequiresPermissions("pms:skuinfo:remove")
    @Log(title = "sku信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(pmsSkuInfoService.deletePmsSkuInfoByIds(ids));
    }

    @RequiresPermissions("pms:skuinfo:importTemplate")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        System.out.println("测试成功！");
        return util.importTemplateExcel("用户数据");
    }


}
