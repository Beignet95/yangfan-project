package com.ruoyi.project.pms.advertisingFee.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
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
import com.ruoyi.project.pms.advertisingFee.domain.AdvertisingFee;
import com.ruoyi.project.pms.advertisingFee.service.IAdvertisingFeeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 广告费费用Controller
 * 
 * @author Beignet
 * @date 2021-03-12
 */
@Controller
@RequestMapping("/pms/advertisingFee")
public class AdvertisingFeeController extends BaseController
{
    private String prefix = "pms/advertisingFee";

    @Autowired
    private IAdvertisingFeeService advertisingFeeService;

    private static final int NO_USE_ROWNUM=1;

    @RequiresPermissions("pms:advertisingFee:view")
    @GetMapping()
    public String advertisingFee()
    {
        return prefix + "/advertisingFee";
    }

    /**
     * 查询广告费费用列表
     */
    @RequiresPermissions("pms:advertisingFee:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(AdvertisingFee advertisingFee)
    {
        startPage();
        List<AdvertisingFee> list = advertisingFeeService.selectAdvertisingFeeList(advertisingFee);
        return getDataTable(list);
    }

    /**
     * 导出广告费费用列表
     */
    @RequiresPermissions("pms:advertisingFee:export")
    @Log(title = "广告费费用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AdvertisingFee advertisingFee)
    {
        List<AdvertisingFee> list = advertisingFeeService.selectAdvertisingFeeList(advertisingFee);
        ExcelUtil<AdvertisingFee> util = new ExcelUtil<AdvertisingFee>(AdvertisingFee.class);
        return util.exportExcel(list, "advertisingFee");
    }

    /**
     * 新增广告费费用
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存广告费费用
     */
    @RequiresPermissions("pms:advertisingFee:add")
    @Log(title = "广告费费用", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(AdvertisingFee advertisingFee)
    {
        return toAjax(advertisingFeeService.insertAdvertisingFee(advertisingFee));
    }

    /**
     * 修改广告费费用
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        AdvertisingFee advertisingFee = advertisingFeeService.selectAdvertisingFeeById(id);
        mmap.put("advertisingFee", advertisingFee);
        return prefix + "/edit";
    }

    /**
     * 修改保存广告费费用
     */
    @RequiresPermissions("pms:advertisingFee:edit")
    @Log(title = "广告费费用", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(AdvertisingFee advertisingFee)
    {
        return toAjax(advertisingFeeService.updateAdvertisingFee(advertisingFee));
    }

    /**
     * 删除广告费费用
     */
    @RequiresPermissions("pms:advertisingFee:remove")
    @Log(title = "广告费费用", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(advertisingFeeService.deleteAdvertisingFeeByIds(ids));
    }

    @Log(title = "广告费费用导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("pms:advertisingFee:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport,String site) throws Exception {

        String filename =  file.getOriginalFilename();
        Date month;
        if(filename.contains("-")){
            String[] strs = filename.split("-");
            String timeAndSuffix = strs[strs.length-1];
            String timeStr = timeAndSuffix.split("\\.")[0];
            try{
                month = DateUtils.parseDate(timeStr,"yyyy年MM月");
            }catch (ParseException e){
                throw new BusinessException("文件名后面必须加上格式为 -yyyy年MM月 的格式的时间信息！");
            }
        }else throw new BusinessException("文件名后面必须加上格式为 -yyyy年MM月 的格式的时间信息！");
        if(StringUtils.isEmpty(site)) throw new BusinessException("请输入站点编码！");

        ExcelUtil<AdvertisingFee> util = new ExcelUtil<AdvertisingFee>(AdvertisingFee.class);
        List<AdvertisingFee> advertisingFeeList = util.importExcel(StringUtils.EMPTY,file.getInputStream(),NO_USE_ROWNUM);
        String message = advertisingFeeService.importAdvertisingFee(advertisingFeeList, updateSupport,month,site);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("pms:advertisingFee:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<AdvertisingFee> util = new ExcelUtil<AdvertisingFee>(AdvertisingFee.class);
        return util.importTemplateExcel("广告费费用",NO_USE_ROWNUM);
    }
}
