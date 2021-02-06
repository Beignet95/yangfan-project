package com.ruoyi.project.oms.orderReturnRepeat.controller;

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
import com.ruoyi.project.oms.orderReturnRepeat.domain.OrderReturnRepeat;
import com.ruoyi.project.oms.orderReturnRepeat.service.IOrderReturnRepeatService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 退货重复数据Controller
 * 
 * @author Beignet
 * @date 2021-02-03
 */
@Controller
@RequestMapping("/oms/orderReturnRepeat")
public class OrderReturnRepeatController extends BaseController
{
    private String prefix = "oms/orderReturnRepeat";

    @Autowired
    private IOrderReturnRepeatService orderReturnRepeatService;

    @RequiresPermissions("oms:orderReturnRepeat:view")
    @GetMapping()
    public String orderReturnRepeat(String repeatId,ModelMap mmap)
    {
        mmap.addAttribute("repeatId",repeatId);
        return prefix + "/orderReturnRepeat";
    }

    /**
     * 查询退货重复数据列表
     */
    @RequiresPermissions("oms:orderReturnRepeat:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OrderReturnRepeat orderReturnRepeat)
    {
        startPage();
        List<OrderReturnRepeat> list = orderReturnRepeatService.selectOrderReturnRepeatList(orderReturnRepeat);
        return getDataTable(list);
    }

    /**
     * 导出退货重复数据列表
     */
    @RequiresPermissions("oms:orderReturnRepeat:export")
    @Log(title = "退货重复数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderReturnRepeat orderReturnRepeat)
    {
        List<OrderReturnRepeat> list = orderReturnRepeatService.selectOrderReturnRepeatList(orderReturnRepeat);
        ExcelUtil<OrderReturnRepeat> util = new ExcelUtil<OrderReturnRepeat>(OrderReturnRepeat.class);
        return util.exportExcel(list, "orderReturnRepeat");
    }

    /**
     * 新增退货重复数据
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存退货重复数据
     */
    @RequiresPermissions("oms:orderReturnRepeat:add")
    @Log(title = "退货重复数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OrderReturnRepeat orderReturnRepeat)
    {
        return toAjax(orderReturnRepeatService.insertOrderReturnRepeat(orderReturnRepeat));
    }

    /**
     * 修改退货重复数据
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        OrderReturnRepeat orderReturnRepeat = orderReturnRepeatService.selectOrderReturnRepeatById(id);
        mmap.put("orderReturnRepeat", orderReturnRepeat);
        return prefix + "/edit";
    }

    /**
     * 修改保存退货重复数据
     */
    @RequiresPermissions("oms:orderReturnRepeat:edit")
    @Log(title = "退货重复数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OrderReturnRepeat orderReturnRepeat)
    {
        return toAjax(orderReturnRepeatService.updateOrderReturnRepeat(orderReturnRepeat));
    }

    /**
     * 删除退货重复数据
     */
    @RequiresPermissions("oms:orderReturnRepeat:remove")
    @Log(title = "退货重复数据", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(orderReturnRepeatService.deleteOrderReturnRepeatByIds(ids));
    }

    @Log(title = "退货重复数据导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("oms:orderReturnRepeat:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<OrderReturnRepeat> util = new ExcelUtil<OrderReturnRepeat>(OrderReturnRepeat.class);
        List<OrderReturnRepeat> orderReturnRepeatList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = orderReturnRepeatService.importOrderReturnRepeat(orderReturnRepeatList, updateSupport);
        return AjaxResult.success(message);
    }
}
