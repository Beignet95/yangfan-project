package com.ruoyi.project.compdata.controller;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.compdata.service.IAdvertisingService;
import com.ruoyi.project.compdata.service.ICompDataService;
import com.ruoyi.project.compdata.vo.AdVo;
import com.ruoyi.project.compdata.vo.AdvertisingAnalyParamVo;
import com.ruoyi.project.compdata.vo.AdvertisingEchartsVo;
import com.ruoyi.project.compdata.vo.ReturnVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/compData")
public class CompDataController {

    private String prefix = "compdata";

    @Autowired
    ICompDataService compDataService;

    @Autowired
    IAdvertisingService advertisingService;


    /**
     * 广告分析页面
     */
    @GetMapping("/advertisingAnalysis")
    public String advertisingAnalysis(ModelMap mmap)
    {
        return prefix + "/advertising-analysis";
    }

    @Log(title = "综合数据导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("compData:importCompTemplate")
    @PostMapping("/importCompData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file,String year,String month,String filetype, boolean updateSupport) throws Exception
    {
        String filename = file.getOriginalFilename();

        if(filename.equals("退货表.xls")||filename.equals("退货表.xlsx")){
            ExcelUtil<ReturnVO> util = new ExcelUtil<ReturnVO>(ReturnVO.class);
            List<ReturnVO> list = util.importExcel(file.getInputStream());
            //String message = compDataService.importCompData(list, updateSupport);
            String message = "测试";
            return AjaxResult.success(message);
        }else if(filename.equals("广告数据(含退款率)可视化分析报表.xls")||filename.equals("广告数据(含退款率)可视化分析报表.xlsx")){
            ExcelUtil<AdVo> util = new ExcelUtil<AdVo>(AdVo.class);
            List<AdVo> list = util.importExcel("数据源",file.getInputStream());
            //String message = compDataService.importCompData(list, updateSupport);
            String message = advertisingService.importData4AdVo(list,updateSupport);
            return AjaxResult.success(message);
        }else{
            return AjaxResult.success("你所导入的Excel文件名不符合规范！");
        }
    }

    @Log(title = "广告分析数据1", businessType = BusinessType.IMPORT)
    //@RequiresPermissions("compData:importCompTemplate")
    @GetMapping("/getAdvertisingAnalysisData")
    @ResponseBody
    public AjaxResult getAdvertisingAnalysisData(AdvertisingAnalyParamVo advertisingAnalyParamVo) throws Exception
    {
        AdvertisingEchartsVo advertisingEchartsVo = advertisingService.selectAdvertisingEchartsVo(advertisingAnalyParamVo);
        return AjaxResult.success(advertisingEchartsVo);
    }




}
