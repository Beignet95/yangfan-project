package com.ruoyi.project.compdata.stadvertising.controller;

import java.util.List;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.compdata.stadvertising.vo.KeywordAnalyVo;
import com.ruoyi.project.compdata.stadvertising.vo.KeywordEchartsVo;
import com.ruoyi.project.compdata.stadvertising.vo.StadvertisingAnalyVo;
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
import com.ruoyi.project.compdata.stadvertising.domain.Stadvertising;
import com.ruoyi.project.compdata.stadvertising.service.IStadvertisingService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * ST广告数据源Controller
 * 
 * @author Beignet
 * @date 2021-01-25
 */
@Controller
@RequestMapping("/compdata/stadvertising")
public class StadvertisingController extends BaseController
{
    private String prefix = "compdata/stadvertising";

    @Autowired
    private IStadvertisingService stadvertisingService;

    @RequiresPermissions("compdata:stadvertising:view")
    @GetMapping()
    public String stadvertising()
    {
        return prefix + "/stadvertising";
    }

    /**
     * 查询ST广告数据源列表
     */
    @RequiresPermissions("compdata:stadvertising:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Stadvertising stadvertising)
    {
        startPage();
        List<Stadvertising> list = stadvertisingService.selectStadvertisingList(stadvertising);
        //return getDataTable(list);
        TableDataInfo tableDataInfo = getDataTable(list);
        return tableDataInfo;
    }

    /**
     * 导出ST广告数据源列表
     */
    @RequiresPermissions("compdata:stadvertising:export")
    @Log(title = "ST广告数据源", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Stadvertising stadvertising)
    {
        List<Stadvertising> list = stadvertisingService.selectStadvertisingList(stadvertising);
        ExcelUtil<Stadvertising> util = new ExcelUtil<Stadvertising>(Stadvertising.class);
        return util.exportExcel(list, "stadvertising");
    }

    /**
     * 新增ST广告数据源
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存ST广告数据源
     */
    @RequiresPermissions("compdata:stadvertising:add")
    @Log(title = "ST广告数据源", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Stadvertising stadvertising)
    {
        return toAjax(stadvertisingService.insertStadvertising(stadvertising));
    }

    /**
     * 修改ST广告数据源
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Stadvertising stadvertising = stadvertisingService.selectStadvertisingById(id);
        mmap.put("stadvertising", stadvertising);
        return prefix + "/edit";
    }

    /**
     * 修改保存ST广告数据源
     */
    @RequiresPermissions("compdata:stadvertising:edit")
    @Log(title = "ST广告数据源", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Stadvertising stadvertising)
    {
        return toAjax(stadvertisingService.updateStadvertising(stadvertising));
    }

    /**
     * 删除ST广告数据源
     */
    @RequiresPermissions("compdata:stadvertising:remove")
    @Log(title = "ST广告数据源", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(stadvertisingService.deleteStadvertisingByIds(ids));
    }

    @Log(title = "ST广告数据数据导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("compdata:stadvertising:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Stadvertising> util = new ExcelUtil<Stadvertising>(Stadvertising.class);
        List<Stadvertising> stadvertisingList = util.importExcel(StringUtils.EMPTY,file.getInputStream());
        String message = stadvertisingService.impStadvertising(stadvertisingList, updateSupport);
        return AjaxResult.success(message);
    }

    /**
     * 广告分析页面
     */
    @GetMapping("/analysis")
    @RequiresPermissions("compdata:stadvertising:analysis")
    public String analysis(ModelMap mmap)
    {
        return prefix + "/analysis";
    }

    /**
     * 查询ST广告分析数据列表
     */
    @RequiresPermissions("compdata:stadvertising:analysis")
    @PostMapping("/analyVolist")
    @ResponseBody
    public TableDataInfo analyVolist(Stadvertising stadvertising)
    {
        startPage();
        List<StadvertisingAnalyVo> list = stadvertisingService.selectStadvertisingAnalyVoList(stadvertising);
        //List<Stadvertising> list2 = stadvertisingService.selectStadvertisingList(stadvertising);
        //return getDataTable(list);
        int count = stadvertisingService.selectCount(stadvertising);
        TableDataInfo tableDataInfo = getDataTable(list);
        tableDataInfo.setTotal(count);
        return tableDataInfo;

    }

    /**
     * 关键词趋势可视化分析页面
     */
    @GetMapping("/keywordAnalysis")
    @RequiresPermissions("compdata:stadvertising:analysis")
    public String keywordAnalysis(ModelMap mmap)
    {
        return prefix + "/keyword_analysis";
    }



    /**
     * 关键词趋势可视化分析页面
     */
    @PostMapping("/getKeywordAnalysisData")
    @RequiresPermissions("compdata:stadvertising:analysis")
    @ResponseBody
    public TableDataInfo getKeywordAnalysisData(KeywordAnalyVo KeywordAnalyVo) throws IllegalAccessException {
        startPage();
        List<KeywordAnalyVo> keywordAnalyVo = stadvertisingService.selectKeywordAnalysisData(KeywordAnalyVo);
        return getDataTable(keywordAnalyVo);
    }

    /**
     * 关键词趋势可视化分析页面
     */
    @PostMapping("/getKeywordEchartsData")
    @RequiresPermissions("compdata:stadvertising:analysis")
    @ResponseBody
    public TableDataInfo getKeywordEchartsVo(Stadvertising stadvertising) throws IllegalAccessException {
        startPage();
        //TODO
        KeywordEchartsVo keywordAnalyVo = stadvertisingService.selectAdvertisingEchartsVo(stadvertising);
        //return getDataTable(keywordAnalyVo);
        return null;
    }

}
