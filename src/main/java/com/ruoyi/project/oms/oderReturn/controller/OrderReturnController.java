package com.ruoyi.project.oms.oderReturn.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.oms.oderReturn.domain.OrderReturn;
import com.ruoyi.project.oms.oderReturn.service.IOrderReturnService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 退货Controller
 * 
 * @author Beignet
 * @date 2021-02-03
 */
@Controller
@RequestMapping("/oms/oderReturn")
public class OrderReturnController extends BaseController
{
    private String prefix = "oms/oderReturn";

    @Autowired
    private IOrderReturnService orderReturnService;

    @RequiresPermissions("oms:oderReturn:view")
    @GetMapping()
    public String oderReturn(String searchKey, ModelMap mmap)
    {
        if(StringUtils.isNotEmpty(searchKey)){
            String[] strs = searchKey.split("_");
            mmap.addAttribute("orderId",strs[0]);
            mmap.addAttribute("sku",strs.length>1?strs[1]:"");
        }

        return prefix + "/oderReturn";
    }

    /**
     * 查询退货列表
     */
    @RequiresPermissions("oms:oderReturn:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OrderReturn orderReturn)
    {
        startPage();
        List<OrderReturn> list = orderReturnService.selectOrderReturnList(orderReturn);
        return getDataTable(list);
    }

    /**
     * 导出退货列表
     */
    @RequiresPermissions("oms:oderReturn:export")
    @Log(title = "退货", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderReturn orderReturn)
    {
        List<OrderReturn> list = orderReturnService.selectOrderReturnList(orderReturn);
        ExcelUtil<OrderReturn> util = new ExcelUtil<OrderReturn>(OrderReturn.class);
        return util.exportExcel(list, "oderReturn");
    }

    /**
     * 新增退货
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存退货
     */
    @RequiresPermissions("oms:oderReturn:add")
    @Log(title = "退货", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OrderReturn orderReturn)
    {
        return toAjax(orderReturnService.insertOrderReturn(orderReturn));
    }

    /**
     * 修改退货
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        OrderReturn orderReturn = orderReturnService.selectOrderReturnById(id);
        mmap.put("orderReturn", orderReturn);
        return prefix + "/edit";
    }

    /**
     * 修改保存退货
     */
    @RequiresPermissions("oms:oderReturn:edit")
    @Log(title = "退货", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OrderReturn orderReturn)
    {
        return toAjax(orderReturnService.updateOrderReturn(orderReturn));
    }

    /**
     * 删除退货
     */
    @RequiresPermissions("oms:oderReturn:remove")
    @Log(title = "退货", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(orderReturnService.deleteOrderReturnByIds(ids));
    }

    @Log(title = "退货导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("oms:oderReturn:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<OrderReturn> util = new ExcelUtil<OrderReturn>(OrderReturn.class);
        List<OrderReturn> oderReturnList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = orderReturnService.importOrderReturn(oderReturnList, updateSupport);
        return AjaxResult.success(message);
    }
}
