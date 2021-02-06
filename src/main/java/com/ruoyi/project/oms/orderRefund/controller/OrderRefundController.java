package com.ruoyi.project.oms.orderRefund.controller;

import java.util.List;

import com.ruoyi.project.oms.orderRefund.service.IOrderRefundService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.oms.orderRefund.domain.OrderRefund;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 退款Controller
 * 
 * @author Beignet
 * @date 2021-02-02
 */
@Controller
@RequestMapping("/oms/ordRefund")
public class OrderRefundController extends BaseController
{
    private String prefix = "oms/ordRefund";

    @Autowired
    private IOrderRefundService OrderRefundService;

    @RequiresPermissions("oms:ordRefund:view")
    @GetMapping()
    public String ordRefund(String searchKey, ModelMap mmap)
    {
        if(StringUtils.isNotEmpty(searchKey)){
            String[] strs = searchKey.split("_");
            mmap.addAttribute("orderId",strs[0]);
            mmap.addAttribute("sku",strs.length>1?strs[1]:"");
        }
        return prefix + "/ordRefund";
    }

    /**
     * 查询退款列表
     */
    @RequiresPermissions("oms:ordRefund:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OrderRefund OrderRefund)
    {
        startPage();
        List<OrderRefund> list = OrderRefundService.selectOrderRefundList(OrderRefund);
        return getDataTable(list);
    }

    /**
     * 导出退款列表
     */
    @RequiresPermissions("oms:ordRefund:export")
    @Log(title = "退款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderRefund OrderRefund)
    {
        List<OrderRefund> list = OrderRefundService.selectOrderRefundList(OrderRefund);
        ExcelUtil<OrderRefund> util = new ExcelUtil<OrderRefund>(OrderRefund.class);
        return util.exportExcel(list, "ordRefund");
    }

    /**
     * 新增退款
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存退款
     */
    @RequiresPermissions("oms:ordRefund:add")
    @Log(title = "退款", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OrderRefund OrderRefund)
    {
        return toAjax(OrderRefundService.insertOrderRefund(OrderRefund));
    }

    /**
     * 修改退款
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        OrderRefund OrderRefund = OrderRefundService.selectOrderRefundById(id);
        mmap.put("OrderRefund", OrderRefund);
        return prefix + "/edit";
    }

    /**
     * 修改保存退款
     */
    @RequiresPermissions("oms:ordRefund:edit")
    @Log(title = "退款", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OrderRefund OrderRefund)
    {
        return toAjax(OrderRefundService.updateOrderRefund(OrderRefund));
    }

    /**
     * 删除退款
     */
    @RequiresPermissions("oms:ordRefund:remove")
    @Log(title = "退款", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(OrderRefundService.deleteOrderRefundByIds(ids));
    }

    @Log(title = "退款导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("oms:ordRefund:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<OrderRefund> util = new ExcelUtil<OrderRefund>(OrderRefund.class);
        List<OrderRefund> ordRefundList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = OrderRefundService.importOrderRefund(ordRefundList, updateSupport);
        return AjaxResult.success(message);
    }

}
