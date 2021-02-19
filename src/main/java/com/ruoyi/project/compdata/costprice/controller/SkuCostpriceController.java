package com.ruoyi.project.compdata.costprice.controller;

import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.pms.relation.domain.AsinTypeRelation;
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
import com.ruoyi.project.compdata.costprice.domain.SkuCostprice;
import com.ruoyi.project.compdata.costprice.service.ISkuCostpriceService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 成本Controller
 * 
 * @author Beignet
 * @date 2021-01-26
 */
@Controller
@RequestMapping("/compdata/costprice")
public class SkuCostpriceController extends BaseController
{
    private String prefix = "compdata/costprice";

    @Autowired
    private ISkuCostpriceService skuCostpriceService;

    @RequiresPermissions("compdata:costprice:view")
    @GetMapping()
    public String costprice()
    {
        return prefix + "/costprice";
    }

    /**
     * 查询成本列表
     */
    @RequiresPermissions("compdata:costprice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SkuCostprice skuCostprice)
    {
        startPage();
        List<SkuCostprice> list = skuCostpriceService.selectSkuCostpriceList(skuCostprice);
        return getDataTable(list);
    }

    /**
     * 导出成本列表
     */
    @RequiresPermissions("compdata:costprice:export")
    @Log(title = "成本", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SkuCostprice skuCostprice)
    {
        List<SkuCostprice> list = skuCostpriceService.selectSkuCostpriceList(skuCostprice);
        ExcelUtil<SkuCostprice> util = new ExcelUtil<SkuCostprice>(SkuCostprice.class);
        return util.exportExcel(list, "costprice");
    }

    /**
     * 新增成本
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存成本
     */
    @RequiresPermissions("compdata:costprice:add")
    @Log(title = "成本", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SkuCostprice skuCostprice)
    {
        return toAjax(skuCostpriceService.insertSkuCostprice(skuCostprice));
    }

    /**
     * 修改成本
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        SkuCostprice skuCostprice = skuCostpriceService.selectSkuCostpriceById(id);
        mmap.put("skuCostprice", skuCostprice);
        return prefix + "/edit";
    }

    /**
     * 修改保存成本
     */
    @RequiresPermissions("compdata:costprice:edit")
    @Log(title = "成本", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SkuCostprice skuCostprice)
    {
        return toAjax(skuCostpriceService.updateSkuCostprice(skuCostprice));
    }

    /**
     * 删除成本
     */
    @RequiresPermissions("compdata:costprice:remove")
    @Log(title = "成本", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(skuCostpriceService.deleteSkuCostpriceByIds(ids));
    }

    @Log(title = "成本数据导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("compdata:costprice:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SkuCostprice> util = new ExcelUtil<SkuCostprice>(SkuCostprice.class);
        List<SkuCostprice> skuCostpriceList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = skuCostpriceService.importSkuCostpriceService(skuCostpriceList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("compdata:costprice:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<SkuCostprice> util = new ExcelUtil<SkuCostprice>(SkuCostprice.class);
        return util.importTemplateExcel("成本");
    }
}
