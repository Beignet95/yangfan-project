package com.ruoyi.project.pms.badcommodity.controller;

import java.util.List;

import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.oms.oderReturn.vo.OrderReturnVo;
import com.ruoyi.project.pms.badcommodity.vo.BadCommodityExcelExpVO;
import com.ruoyi.project.pms.badcommodity.vo.BadCommodityPicture;
import com.ruoyi.project.system.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.pms.badcommodity.domain.BadCommodity;
import com.ruoyi.project.pms.badcommodity.service.IBadCommodityService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 不良记录Controller
 * 
 * @author Beignet
 * @date 2021-02-01
 */
@Slf4j
@Controller
@RequestMapping("/pms/badcommodity")
public class BadCommodityController extends BaseController
{
    private String prefix = "pms/badcommodity";

    @Autowired
    private IBadCommodityService badCommodityService;

    @RequiresPermissions("pms:badcommodity:view")
    @GetMapping()
    public String badcommodity()
    {
        return prefix + "/badcommodity";
    }

    /**
     * 查询不良记录列表
     */
    @RequiresPermissions("pms:badcommodity:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BadCommodity badCommodity)
    {
        startPage();
        List<BadCommodity> list = badCommodityService.selectBadCommodityList(badCommodity);
        return getDataTable(list);
    }

    /**
     * 导出不良记录列表
     */
    @RequiresPermissions("pms:badcommodity:export")
    @Log(title = "不良记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BadCommodity badCommodity)
    {
        List<BadCommodityExcelExpVO> list = badCommodityService.selectBadCommodityExcelExpVoList(badCommodity);
        ExcelUtil<BadCommodityExcelExpVO> util = new ExcelUtil<BadCommodityExcelExpVO>(BadCommodityExcelExpVO.class);
        return util.exportExcel(list, "badcommodity");
    }

    /**
     * 新增不良记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存不良记录
     */
    @RequiresPermissions("pms:badcommodity:add")
    @Log(title = "不良记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSaveWithPic(BadCommodity badCommodity,@RequestParam("pic")List<MultipartFile> files)
    {
        //return toAjax(badCommodityService.insertBadCommodity(badCommodity));
        return toAjax(badCommodityService.insertBadCommodityAndUploadPicture(badCommodity,files));
    }

    /**
     * 上传不良图片
     */
    @RequiresPermissions("pms:badcommodity:add")
    @Log(title = "不良记录", businessType = BusinessType.INSERT)
    @PostMapping("/uploadBadRecordPicture")
    @ResponseBody
    public AjaxResult uploadBadRecordPicture(BadCommodity badCommodity,@RequestParam("pic")MultipartFile file)
    {
        try{
            BadCommodityPicture badCommodityPicture = badCommodityService.uploadBadRecordPicture(file,badCommodity);
            return AjaxResult.success(badCommodityPicture);
        }catch (Exception e){
            return AjaxResult.error("上传失败！");
        }
    }


    /**
     * 删除不良图片
     */
    //@RequiresPermissions("pms:badcommodity:add")
    @Log(title = "不良记录", businessType = BusinessType.UPDATE)
    @PostMapping("/deleteBadRecordPicture")
    @ResponseBody
    public AjaxResult deleteBadRecordPicture(@RequestParam("id")Long id,@RequestParam("picNo") String picNo)
    {
        return toAjax(badCommodityService.deleteBadRecordPicture(id,picNo));//toAjax(badCommodityService.insertBadCommodity(badCommodity));
    }

    /**
     * 修改不良记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BadCommodity badCommodity = badCommodityService.selectBadCommodityById(id);
        mmap.put("badCommodity", badCommodity);
        return prefix + "/edit";
    }

    /**
     * 修改保存不良记录
     */
    @RequiresPermissions("pms:badcommodity:edit")
    @Log(title = "不良记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BadCommodity badCommodity)
    {
        return toAjax(badCommodityService.updateBadCommodity(badCommodity));
    }

    /**
     * 删除不良记录
     */
    @RequiresPermissions("pms:badcommodity:remove")
    @Log(title = "不良记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(badCommodityService.deleteBadCommodityByIds(ids));
    }

    @Log(title = "不良记录导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("pms:badcommodity:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<BadCommodity> util = new ExcelUtil<BadCommodity>(BadCommodity.class);
        List<BadCommodity> badcommodityList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = badCommodityService.importBadCommodity(badcommodityList, updateSupport);
        return AjaxResult.success(message);
    }


    /**
     * 获取订单退货Vo
     */
    @GetMapping("/getOrderReturnVo")
    @ResponseBody
    public AjaxResult getOrderReturnVo(@RequestParam("orderId") String orderId,@RequestParam("sku")String sku, ModelMap mmap)
    {
        //TODO
        BadCommodity badCommodity = new BadCommodity();
        badCommodity.setOrderId(orderId);
        List<BadCommodity> badCommodities = badCommodityService.selectBadCommodityList(badCommodity);
        if(badCommodities.size()>0){
           return AjaxResult.error("已经存在订单号为："+orderId+"的不良记录，不能再次录入");
        }
        OrderReturnVo OrderReturnVo = badCommodityService.selectOrderReturnVo(orderId,sku);
        return AjaxResult.success(OrderReturnVo);
    }

    @RequiresPermissions("pms:badcommodity:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<BadCommodity> util = new ExcelUtil<BadCommodity>(BadCommodity.class);
        return util.importTemplateExcel("不良记录");
    }

    @RequiresPermissions("pms:badcommodity:view")
    @GetMapping("/toOrderReturnPage/{orderId}")
    public String toOrderReturnPage(@PathVariable("orderId") String orderId,ModelMap mmap)
    {
        mmap.addAttribute("orderId",orderId);
        return prefix+"/order_return_record_page";
    }
}
