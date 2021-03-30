package com.ruoyi.project.oms.refundServicefee.controller;

import java.util.Date;
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
import com.ruoyi.project.oms.refundServicefee.domain.RefundServicefee;
import com.ruoyi.project.oms.refundServicefee.service.IRefundServicefeeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 平台退款服务费Controller
 * 
 * @author Beignet
 * @date 2021-03-10
 */
@Controller
@RequestMapping("/oms/refundServicefee")
public class RefundServicefeeController extends BaseController
{
    private String prefix = "oms/refundServicefee";

    @Autowired
    private IRefundServicefeeService refundServicefeeService;

    @RequiresPermissions("oms:refundServicefee:view")
    @GetMapping()
    public String refundServicefee()
    {
        return prefix + "/refundServicefee";
    }

    /**
     * 查询平台退款服务费列表
     */
    @RequiresPermissions("oms:refundServicefee:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(RefundServicefee refundServicefee)
    {
        startPage();
        List<RefundServicefee> list = refundServicefeeService.selectRefundServicefeeList(refundServicefee);
        return getDataTable(list);
    }

    /**
     * 导出平台退款服务费列表
     */
    @RequiresPermissions("oms:refundServicefee:export")
    @Log(title = "平台退款服务费", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(RefundServicefee refundServicefee)
    {
        List<RefundServicefee> list = refundServicefeeService.selectRefundServicefeeList(refundServicefee);
        ExcelUtil<RefundServicefee> util = new ExcelUtil<RefundServicefee>(RefundServicefee.class);
        return util.exportExcel(list, "refundServicefee");
    }

    /**
     * 新增平台退款服务费
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存平台退款服务费
     */
    @RequiresPermissions("oms:refundServicefee:add")
    @Log(title = "平台退款服务费", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(RefundServicefee refundServicefee)
    {
        return toAjax(refundServicefeeService.insertRefundServicefee(refundServicefee));
    }

    /**
     * 修改平台退款服务费
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        RefundServicefee refundServicefee = refundServicefeeService.selectRefundServicefeeById(id);
        mmap.put("refundServicefee", refundServicefee);
        return prefix + "/edit";
    }

    /**
     * 修改保存平台退款服务费
     */
    @RequiresPermissions("oms:refundServicefee:edit")
    @Log(title = "平台退款服务费", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(RefundServicefee refundServicefee)
    {
        return toAjax(refundServicefeeService.updateRefundServicefee(refundServicefee));
    }

    /**
     * 删除平台退款服务费
     */
    @RequiresPermissions("oms:refundServicefee:remove")
    @Log(title = "平台退款服务费", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(refundServicefeeService.deleteRefundServicefeeByIds(ids));
    }

    @Log(title = "平台退款服务费导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("oms:refundServicefee:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport, String account, Date month) throws Exception
    {
        ExcelUtil<RefundServicefee> util = new ExcelUtil<RefundServicefee>(RefundServicefee.class);
        List<RefundServicefee> refundServicefeeList = util.importExcel(StringUtils.EMPTY,file.getInputStream(),3);
        String message = refundServicefeeService.importRefundServicefee(refundServicefeeList, updateSupport,account,month);
        return AjaxResult.success(message);
    }

    /**
     * 展示导入框
     */
    @GetMapping("/showImportPage")
    public String showImportPage(boolean showSpareField, ModelMap mmap)
    {
        mmap.addAttribute("showSpareField",showSpareField);
        return prefix + "/import";
    }

    /**
     * 解锁并删除数据
     */
    @PostMapping("/unlockData")
    @RequiresPermissions("oms:refundServicefee:remove")
    @Log(title = "解锁移除明细", businessType = BusinessType.DELETE)
    @ResponseBody
    public AjaxResult unlockData(Date month,String account)
    {
        int unlockNum = refundServicefeeService.unlockData(month,account);
        if(unlockNum>-1){
            return AjaxResult.success("成功结算并删除"+unlockNum+"条数据！");
        }else{
            return AjaxResult.success("数据未曾锁定！无需解锁!");
        }
    }
}
