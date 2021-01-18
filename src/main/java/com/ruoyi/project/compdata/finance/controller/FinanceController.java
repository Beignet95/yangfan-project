package com.ruoyi.project.compdata.finance.controller;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.compdata.finance.vo.FinanceAnalyParamVo;
import com.ruoyi.project.compdata.finance.vo.FinanceEchartsVo;
import com.ruoyi.project.compdata.vo.AdvertisingAnalyParamVo;
import com.ruoyi.project.compdata.vo.AdvertisingAnalySearchVo;
import com.ruoyi.project.compdata.vo.AdvertisingEchartsVo;
import com.ruoyi.project.system.user.domain.User;
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
import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.compdata.finance.service.IFinanceService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 财务数据Controller
 * 
 * @author Beignet
 * @date 2021-01-13
 */
@Controller
@RequestMapping("/compdata/finance")
public class FinanceController extends BaseController
{
    private String prefix = "compdata/finance";

    @Autowired
    private IFinanceService financeService;

    @RequiresPermissions("compdata:finance:view")
    @GetMapping()
    public String finance()
    {
        return prefix + "/finance";
    }

    /**
     * 查询财务数据列表
     */
    @RequiresPermissions("compdata:finance:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Finance finance)
    {
        startPage();
        List<Finance> list = financeService.selectFinanceList(finance);
        return getDataTable(list);
    }

    /**
     * 导出财务数据列表
     */
    @RequiresPermissions("compdata:finance:export")
    @Log(title = "财务数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Finance finance)
    {
        List<Finance> list = financeService.selectFinanceList(finance);
        ExcelUtil<Finance> util = new ExcelUtil<Finance>(Finance.class);
        return util.exportExcel(list, "finance");
    }

    /**
     * 新增财务数据
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存财务数据
     */
    @RequiresPermissions("compdata:finance:add")
    @Log(title = "财务数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Finance finance)
    {
        return toAjax(financeService.insertFinance(finance));
    }

    /**
     * 修改财务数据
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Finance finance = financeService.selectFinanceById(id);
        mmap.put("finance", finance);
        return prefix + "/edit";
    }

    /**
     * 修改保存财务数据
     */
    @RequiresPermissions("compdata:finance:edit")
    @Log(title = "财务数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Finance finance)
    {
        return toAjax(financeService.updateFinance(finance));
    }

    /**
     * 删除财务数据
     */
    @RequiresPermissions("compdata:finance:remove")
    @Log(title = "财务数据", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(financeService.deleteFinanceByIds(ids));
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("compdata:finance:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Finance> util = new ExcelUtil<Finance>(Finance.class);
        //List<Finance> financeList = null;
        List<Finance> financeList = util.importExcel(StringUtils.EMPTY,file.getInputStream(),16);
        List<Finance> financeList2 = util.importExcel(StringUtils.EMPTY,file.getInputStream(),16);
        List<Finance> financeList3 = util.importExcel(StringUtils.EMPTY,file.getInputStream(),17);
        List<Finance> financeList4 = util.importExcel(StringUtils.EMPTY,file.getInputStream(),18);
        List<Finance> financeList5 = util.importExcel(StringUtils.EMPTY,file.getInputStream(),14);
        String message = financeService.importFinance(financeList, updateSupport);
        return AjaxResult.success(message);
    }

    /**
     * 财务分析页面
     */
    @GetMapping("/analysis")
    @RequiresPermissions("compdata:finance:analysis")
    public String analysis(ModelMap mmap)
    {
        return prefix + "/analysis";
    }

    @Log(title = "财务分析数据", businessType = BusinessType.IMPORT)
    //@RequiresPermissions("compData:importCompTemplate")
    @GetMapping("/getAnalysisData")
    //@RequiresPermissions("compdata:finance:analysis")
    @ResponseBody
    public AjaxResult getAnalysisData(Finance finance) throws Exception
    {
        FinanceEchartsVo financeEchartsVo = financeService.selectFinanceEchartsVo(finance);
        return AjaxResult.success(financeEchartsVo);
    }


    /**
     * 获取搜索的关键信息
     */
    @GetMapping("/getAnalySearch")
    @ResponseBody
    public AjaxResult getAnalySearch(FinanceAnalyParamVo financeAnalyParamVo, ModelMap mmap)
    {
          Map searchMap = financeService.selectAnalySearch(financeAnalyParamVo);
//        return AjaxResult.success(advertisingAnalySearchVo);
        return AjaxResult.success(searchMap);
    }
}
