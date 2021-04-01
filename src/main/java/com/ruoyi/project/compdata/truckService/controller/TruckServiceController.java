package com.ruoyi.project.compdata.truckService.controller;

import java.util.List;

import com.ruoyi.common.exception.BusinessException;
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
import com.ruoyi.project.compdata.truckService.domain.TruckService;
import com.ruoyi.project.compdata.truckService.service.ITruckServiceService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 卡车费用Controller
 * 
 * @author Beignet
 * @date 2021-03-06
 */
@Controller
@RequestMapping("/lms/truckService")
public class TruckServiceController extends BaseController
{
    private String prefix = "lms/truckService";

    @Autowired
    private ITruckServiceService truckServiceService;

    @RequiresPermissions("lms:truckService:view")
    @GetMapping()
    public String truckService()
    {
        return prefix + "/truckService";
    }

    /**
     * 查询卡车费用列表
     */
    @RequiresPermissions("lms:truckService:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TruckService truckService)
    {
        startPage();
        List<TruckService> list = truckServiceService.selectTruckServiceList(truckService);
        return getDataTable(list);
    }

    /**
     * 导出卡车费用列表
     */
    @RequiresPermissions("lms:truckService:export")
    @Log(title = "卡车费用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TruckService truckService)
    {
        List<TruckService> list = truckServiceService.selectTruckServiceList(truckService);
        ExcelUtil<TruckService> util = new ExcelUtil<TruckService>(TruckService.class);
        return util.exportExcel(list, "truckService");
    }

    /**
     * 新增卡车费用
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存卡车费用
     */
    @RequiresPermissions("lms:truckService:add")
    @Log(title = "卡车费用", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TruckService truckService)
    {
        return toAjax(truckServiceService.insertTruckService(truckService));
    }

    /**
     * 修改卡车费用
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TruckService truckService = truckServiceService.selectTruckServiceById(id);
        mmap.put("truckService", truckService);
        return prefix + "/edit";
    }

    /**
     * 修改保存卡车费用
     */
    @RequiresPermissions("lms:truckService:edit")
    @Log(title = "卡车费用", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TruckService truckService)
    {
        return toAjax(truckServiceService.updateTruckService(truckService));
    }

    /**
     * 删除卡车费用
     */
    @RequiresPermissions("lms:truckService:remove")
    @Log(title = "卡车费用", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(truckServiceService.deleteTruckServiceByIds(ids));
    }

    @Log(title = "卡车费用导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("lms:truckService:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport,Long truckRecordId) throws Exception
    {
        if(truckRecordId==null)   throw new BusinessException("非法导入！");
        ExcelUtil<TruckService> util = new ExcelUtil<TruckService>(TruckService.class);
        List<TruckService> truckServiceList = util.importExcel(StringUtils.EMPTY,file.getInputStream(),noImportRowNum);
        String message = truckServiceService.importTruckService(truckServiceList, updateSupport,truckRecordId);
        return AjaxResult.success(message);
    }

    private final static int noImportRowNum = 8;
    //导出 导入模板
    @RequiresPermissions("lms:truckService:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<TruckService> util = new ExcelUtil<TruckService>(TruckService.class);
        return util.importTemplateExcel("卡车费用",noImportRowNum);
    }
}
