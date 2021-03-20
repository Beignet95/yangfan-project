package com.ruoyi.project.oms.orderRecord.controller;

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
import com.ruoyi.project.oms.orderRecord.domain.OrderRecord;
import com.ruoyi.project.oms.orderRecord.service.IOrderRecordService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 订单记录Controller
 * 
 * @author Beignet
 * @date 2021-03-19
 */
@Controller
@RequestMapping("/oms/orderRecord")
public class OrderRecordController extends BaseController
{
    private String prefix = "oms/orderRecord";

    @Autowired
    private IOrderRecordService orderRecordService;

    @RequiresPermissions("oms:orderRecord:view")
    @GetMapping()
    public String orderRecord()
    {
        return prefix + "/orderRecord";
    }

    /**
     * 查询订单记录列表
     */
    @RequiresPermissions("oms:orderRecord:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OrderRecord orderRecord)
    {
        startPage();
        List<OrderRecord> list = orderRecordService.selectOrderRecordList(orderRecord);
        return getDataTable(list);
    }

    /**
     * 导出订单记录列表
     */
    @RequiresPermissions("oms:orderRecord:export")
    @Log(title = "订单记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderRecord orderRecord)
    {
        List<OrderRecord> list = orderRecordService.selectOrderRecordList(orderRecord);
        ExcelUtil<OrderRecord> util = new ExcelUtil<OrderRecord>(OrderRecord.class);
        return util.exportExcel(list, "orderRecord");
    }

    /**
     * 新增订单记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存订单记录
     */
    @RequiresPermissions("oms:orderRecord:add")
    @Log(title = "订单记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OrderRecord orderRecord)
    {
        return toAjax(orderRecordService.insertOrderRecord(orderRecord));
    }

    /**
     * 修改订单记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        OrderRecord orderRecord = orderRecordService.selectOrderRecordById(id);
        mmap.put("orderRecord", orderRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存订单记录
     */
    @RequiresPermissions("oms:orderRecord:edit")
    @Log(title = "订单记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OrderRecord orderRecord)
    {
        return toAjax(orderRecordService.updateOrderRecord(orderRecord));
    }

    /**
     * 删除订单记录
     */
    @RequiresPermissions("oms:orderRecord:remove")
    @Log(title = "订单记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(orderRecordService.deleteOrderRecordByIds(ids));
    }

    @Log(title = "订单记录导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("oms:orderRecord:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<OrderRecord> util = new ExcelUtil<OrderRecord>(OrderRecord.class);
        List<OrderRecord> orderRecordList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = orderRecordService.importOrderRecord(orderRecordList, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("oms:orderRecord:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<OrderRecord> util = new ExcelUtil<OrderRecord>(OrderRecord.class);
        return util.importTemplateExcel("订单记录");
    }
}
