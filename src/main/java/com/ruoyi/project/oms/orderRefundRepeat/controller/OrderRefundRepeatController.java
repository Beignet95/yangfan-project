package com.ruoyi.project.oms.orderRefundRepeat.controller;

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
import com.ruoyi.project.oms.orderRefundRepeat.domain.OrderRefundRepeat;
import com.ruoyi.project.oms.orderRefundRepeat.service.IOrderRefundRepeatService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 退款(重复数据）Controller
 * 
 * @author Beignet
 * @date 2021-02-02
 */
@Controller
@RequestMapping("/oms/orderRefundRepeat")
public class OrderRefundRepeatController extends BaseController
{
    private String prefix = "oms/orderRefundRepeat";

    @Autowired
    private IOrderRefundRepeatService orderRefundRepeatService;

    @RequiresPermissions("oms:orderRefundRepeat:view")
    @GetMapping()
    public String orderRefundRepeat(String repeatId,ModelMap mmap)
    {
        mmap.addAttribute("repeatId",repeatId);
        return prefix + "/orderRefundRepeat";
    }

    /**
     * 查询退款(重复数据）列表
     */
    @RequiresPermissions("oms:orderRefundRepeat:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OrderRefundRepeat orderRefundRepeat)
    {
        startPage();
        List<OrderRefundRepeat> list = orderRefundRepeatService.selectOrderRefundRepeatList(orderRefundRepeat);
        return getDataTable(list);
    }

    /**
     * 导出退款(重复数据）列表
     */
    @RequiresPermissions("oms:orderRefundRepeat:export")
    @Log(title = "退款(重复数据）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderRefundRepeat orderRefundRepeat)
    {
        List<OrderRefundRepeat> list = orderRefundRepeatService.selectOrderRefundRepeatList(orderRefundRepeat);
        ExcelUtil<OrderRefundRepeat> util = new ExcelUtil<OrderRefundRepeat>(OrderRefundRepeat.class);
        return util.exportExcel(list, "orderRefundRepeat");
    }

    /**
     * 新增退款(重复数据）
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存退款(重复数据）
     */
    @RequiresPermissions("oms:orderRefundRepeat:add")
    @Log(title = "退款(重复数据）", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OrderRefundRepeat orderRefundRepeat)
    {
        return toAjax(orderRefundRepeatService.insertOrderRefundRepeat(orderRefundRepeat));
    }

    /**
     * 修改退款(重复数据）
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        OrderRefundRepeat orderRefundRepeat = orderRefundRepeatService.selectOrderRefundRepeatById(id);
        mmap.put("orderRefundRepeat", orderRefundRepeat);
        return prefix + "/edit";
    }

    /**
     * 修改保存退款(重复数据）
     */
    @RequiresPermissions("oms:orderRefundRepeat:edit")
    @Log(title = "退款(重复数据）", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OrderRefundRepeat orderRefundRepeat)
    {
        return toAjax(orderRefundRepeatService.updateOrderRefundRepeat(orderRefundRepeat));
    }

    /**
     * 删除退款(重复数据）
     */
    @RequiresPermissions("oms:orderRefundRepeat:remove")
    @Log(title = "退款(重复数据）", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(orderRefundRepeatService.deleteOrderRefundRepeatByIds(ids));
    }

    @Log(title = "退款(重复数据）导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("oms:orderRefundRepeat:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<OrderRefundRepeat> util = new ExcelUtil<OrderRefundRepeat>(OrderRefundRepeat.class);
        List<OrderRefundRepeat> orderRefundRepeatList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = orderRefundRepeatService.importOrderRefundRepeat(orderRefundRepeatList, updateSupport);
        return AjaxResult.success(message);
    }

    /**
     * 导出退款(重复数据）列表
     */
    //@RequiresPermissions("oms:orderRefundRepeat:export")
    @Log(title = "退款(重复数据）", businessType = BusinessType.EXPORT)
    @GetMapping("/downRepeatData")
    @ResponseBody
    public AjaxResult downRepeatData(OrderRefundRepeat orderRefundRepeat)
    {
        List<OrderRefundRepeat> list = orderRefundRepeatService.selectOrderRefundRepeatList(orderRefundRepeat);
        ExcelUtil<OrderRefundRepeat> util = new ExcelUtil<OrderRefundRepeat>(OrderRefundRepeat.class);
        return util.exportExcel(list, "orderRefundRepeat");
    }
}
