package com.ruoyi.project.pms.registration.controller;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExportExcelUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.oms.countries.domain.RemainingCountries;
import com.ruoyi.project.oms.countries.service.IRemainingCountriesService;
import com.ruoyi.project.pms.registration.domain.ReissueRegistration;
import com.ruoyi.project.pms.registration.service.IReissueRegistrationService;
import com.ruoyi.project.pms.weightquery.domain.SkuWeightquery;
import com.ruoyi.project.pms.weightquery.service.ISkuWeightqueryService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * 补发登记Controller
 * 
 * @author Kwl
 * @date 2021-04-01
 */
@Controller
@RequestMapping("/pms/registration")
public class ReissueRegistrationController extends BaseController
{
    private String prefix = "pms/registration";

    @Autowired
    private IReissueRegistrationService reissueRegistrationService;

    @Autowired
    private IRemainingCountriesService iRemainingCountriesService;
    @Autowired
    private ISkuWeightqueryService iSkuWeightqueryService;

    @Autowired
    private IRemainingCountriesService remainingCountriesService;

    @RequiresPermissions("pms:registration:view")
    @GetMapping()
    public String registration()
    {
        return prefix + "/registration";
    }

    /**
     * 查询补发登记列表
     */
    @RequiresPermissions("pms:registration:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ReissueRegistration reissueRegistration , String spareField) {
        startPage();

        if (spareField.equals("Y") || spareField.equals("")) {
            List<ReissueRegistration> list = reissueRegistrationService.selectReissueRegistrationList(reissueRegistration);
            return getDataTable(list);
        } else {
            RemainingCountries remainingCountries = new RemainingCountries();
            List<RemainingCountries> list = remainingCountriesService.selectRemainingCountriesList(remainingCountries);
            return getDataTable(list);
        }
    }

    /**
     * 导出补发登记列表
     */
    @RequiresPermissions("pms:registration:export")
    @Log(title = "补发登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ReissueRegistration reissueRegistration )  {

            // 标题
            String[] title = { "订单号", "客服负责人", "账号", "站点", "易仓SKU", "套数", "个数", "收件人姓名" , "收货地址", "对应货物净重", "对应货物毛重",
                               "跟踪号", "客服备注",  "客服登记日期" , "仓库发货日期" , "物流是否已操作"};
            // 创建一个工作簿
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建英国补发
            HSSFSheet sheet = workbook.createSheet("英国补发");
            // 设置列宽
            setColumnWidth(sheet, 17);
            // 创建第一行
            HSSFRow row = sheet.createRow(0);
            // 创建一个单元格
            HSSFCell cell = null;
            // 创建表头
            for (int i = 0; i < title.length; i++) {
                cell = row.createCell(i);
                // 设置样式
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                // 设置字体
                HSSFFont font = workbook.createFont();
                font.setFontName("宋体");
                // font.setFontHeight((short)12);
                font.setFontHeightInPoints((short) 13);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(title[i]);
            }
            // 模拟数据
        List<ReissueRegistration> list = reissueRegistrationService.selectReissueRegistrationList(reissueRegistration);
            System.out.println(list);
            // 从第二行开始追加数据
            for (int i = 1; i < (list.size() + 1); i++) {
                // 创建第i行
                HSSFRow nextRow = sheet.createRow(i);
                for (int j = 0; j < 17; j++) {
                    ReissueRegistration eQuestion = list.get(i - 1);
                    HSSFCell cell2 = nextRow.createCell(j);
                    if (j == 0) {
                        cell2.setCellValue(eQuestion.getOrderNumber());
                    }
                    if (j == 1) {
                        cell2.setCellValue(eQuestion.getCustomerManager());
                    }
                    if (j == 2) {
                        cell2.setCellValue(eQuestion.getAccountNumber());
                    }
                    if (j == 3) {
                        cell2.setCellValue(eQuestion.getSite());
                    }
                    if (j == 4) {
                        cell2.setCellValue(eQuestion.getWarehouseSku());
                    }
                    if (j == 5) {
                        if (eQuestion.getNumberSets() == null){
                            continue;
                        }else {
                            cell2.setCellValue(eQuestion.getNumberSets());
                        }
                    }
                    if (j == 6) {
                        if (eQuestion.getNumber() == null){
                            continue;
                        }else {
                            cell2.setCellValue(eQuestion.getNumber());
                        }
                    }
                    if (j == 7) {
                        cell2.setCellValue(eQuestion.getRecipientName());
                    }
                    if (j == 8) {
                        cell2.setCellValue(eQuestion.getReceiptAddress());
                    }
                    if (j == 9) {
                        cell2.setCellValue(eQuestion.getGoodsNetweight().toString());
                        //cell2.setCellValue((RichTextString) eQuestion.getGoodsNetweight());
                    }
                    if (j == 10) {
                        cell2.setCellValue(eQuestion.getGoodsGrossweight().toString());
                       // cell2.setCellValue((RichTextString) eQuestion.getGoodsGrossweight());
                    }
                    if (j == 11) {
                        cell2.setCellValue(eQuestion.getTrackingNumber());
                    }
                    if (j == 12) {
                        cell2.setCellValue(eQuestion.getCustomerRemarks());
                    }
                    if (j == 13) {
                        Date customerRegistrationDate = eQuestion.getCustomerRegistrationDate();
                        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                        cell2.setCellValue(dateformat.format(customerRegistrationDate));
                    }
                    if (j == 14) {
                        Date warehouseShipDate = eQuestion.getWarehouseShipDate();
                        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                        cell2.setCellValue(dateformat.format(warehouseShipDate));
                    }
                    if (j == 15) {
                        cell2.setCellValue(eQuestion.getLogisticsOperating());
                    }
                }
            }

            // 标题
            String[] title2 = { "易仓SKU", "数量", "跟踪号", "销售备注", "登记日期", "站点"};
            // 创建其余四国补发
            HSSFSheet sheet2 = workbook.createSheet("其余四国补发");
            setColumnWidth(sheet2, 8);
            HSSFRow row2 = sheet2.createRow(0);
            // 创建一个单元格
            HSSFCell cell3 = null;
                // 创建表头
                for (int i = 0; i < title2.length; i++) {
                    cell3 = row2.createCell(i);
                    // 设置样式
                    HSSFCellStyle cellStyle = workbook.createCellStyle();
                    // 设置字体
                    HSSFFont font = workbook.createFont();
                    font.setFontName("宋体");
                    font.setFontHeightInPoints((short) 13);
                    cellStyle.setFont(font);
                    cell3.setCellStyle(cellStyle);
                    cell3.setCellValue(title2[i]);
                }
                // 模拟数据2
                RemainingCountries remainingCountries = new RemainingCountries();
                List<RemainingCountries> list2 = remainingCountriesService.selectRemainingCountriesList(remainingCountries);
                // 从第二行开始追加数据
                for (int i = 1; i < (list2.size() + 1); i++) {
                    // 创建第i行
                    HSSFRow nextRow2 = sheet2.createRow(i);
                    for (int j = 0; j < 8; j++) {
                        RemainingCountries eQuestion = list2.get(i-1);
                        HSSFCell cell4 = nextRow2.createCell(j);
                        if (j == 0) {
                            cell4.setCellValue(eQuestion.getWarehouseSku());
                        }
                        if (j == 1) {
                           if (eQuestion.getNumberSets() == null){
                                break;
                           }else {
                               cell4.setCellValue(eQuestion.getNumberSets());
                           }
                        }
                        if (j == 2) {
                            cell4.setCellValue(eQuestion.getTrackingNumber());
                        }
                        if (j == 3) {
                            cell4.setCellValue( eQuestion.getCustomerRemarks());
                        }
                        if (j == 4) {
                            cell4.setCellValue( eQuestion.getCustomerRegistrationDate());
                        }
                        if (j == 5) {
                            cell4.setCellValue( eQuestion.getSite());
                        }
                    }
                }


            // 标题
            String[] title3 = { "产品SKU", "产品名称", "品类", "重量", "包装尺寸-长(cm)", "包装尺寸-宽(cm)", "包装尺寸-高(cm)"};
            // 创建SKU净重数据查询
            HSSFSheet sheet3 = workbook.createSheet("SKU净重数据查询");
            setColumnWidth(sheet3, 8);
            HSSFRow row3 = sheet3.createRow(0);
            // 创建一个单元格
            HSSFCell cell5 = null;
            // 创建表头
            for (int i = 0; i < title3.length; i++) {
                cell5 = row3.createCell(i);
                // 设置样式
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                // 设置字体
                HSSFFont font = workbook.createFont();
                font.setFontName("宋体");
                font.setFontHeightInPoints((short) 13);
                cellStyle.setFont(font);
                cell5.setCellStyle(cellStyle);
                cell5.setCellValue(title3[i]);
            }
            // 模拟数据3
            SkuWeightquery skuWeightquery = new SkuWeightquery();
            List<SkuWeightquery> list3 = iSkuWeightqueryService.selectSkuWeightqueryList(skuWeightquery);
            // 从第二行开始追加数据
            for (int i = 1; i < (list3.size() + 1); i++) {
                // 创建第i行
                HSSFRow nextRow3 = sheet3.createRow(i);
                for (int j = 0; j < 8; j++) {
                    SkuWeightquery eQuestion = list3.get(i-1);
                    HSSFCell cell6 = nextRow3.createCell(j);
                    if (j == 0) {
                        cell6.setCellValue(eQuestion.getProductSku());
                    }
                    if (j == 1) {
                            cell6.setCellValue(eQuestion.getProductName());
                    }
                    if (j == 2) {
                        cell6.setCellValue(eQuestion.getCategory());
                    }
                    if (j == 3) {
                        if (eQuestion.getWeight() == null){
                            continue;
                        }else {
                            cell6.setCellValue(eQuestion.getWeight().toString());
                        }
                    }
                    if (j == 4) {
                        cell6.setCellValue(eQuestion.getPackageSizeLong().toString());
                    }
                    if (j == 5) {
                        cell6.setCellValue( eQuestion.getPackageSizeWidth().toString());
                    }
                    if (j == 6) {
                        cell6.setCellValue( eQuestion.getPackageSizeHigh().toString());
                    }
                }
            }




                // 创建一个文件
                File file = new File("D:/补发登记表.xls");
                try {
                    file.createNewFile();
                    // 打开文件流
                    FileOutputStream outputStream = FileUtils.openOutputStream(file);
                    workbook.write(outputStream);
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            }
        return AjaxResult.success();
    }

    // 设置列宽
    private static void setColumnWidth(HSSFSheet sheet, int colNum) {
        for (int i = 0; i < colNum; i++) {
            int v = 0;
            v = Math.round(Float.parseFloat("15.0") * 37F);
            v = Math.round(Float.parseFloat("20.0") * 267.5F);
            sheet.setColumnWidth(i, v);
        }
    }


    /**
     * 新增补发登记
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存补发登记
     */
    @RequiresPermissions("pms:registration:add")
    @Log(title = "补发登记", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ReissueRegistration reissueRegistration)
    {
        return toAjax(reissueRegistrationService.insertReissueRegistration(reissueRegistration));
    }

    /**
     * 修改补发登记
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ReissueRegistration reissueRegistration = reissueRegistrationService.selectReissueRegistrationById(id);
        mmap.put("reissueRegistration", reissueRegistration);
        return prefix + "/edit";
    }

    /**
     * 修改保存补发登记
     */
    @RequiresPermissions("pms:registration:edit")
    @Log(title = "补发登记", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ReissueRegistration reissueRegistration)
    {
        return toAjax(reissueRegistrationService.updateReissueRegistration(reissueRegistration));
    }

    /**
     * 删除补发登记
     */
    @RequiresPermissions("pms:registration:remove")
    @Log(title = "补发登记", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(reissueRegistrationService.deleteReissueRegistrationByIds(ids));
    }

    @Log(title = "补发登记导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("pms:registration:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        String message = null ;
        ExcelUtil<ReissueRegistration> util = new ExcelUtil<ReissueRegistration>(ReissueRegistration.class);
        List<ReissueRegistration> registrationList = util.importExcel(StringUtils.EMPTY,file.getInputStream());

        ExcelUtil<RemainingCountries> utils = new ExcelUtil<RemainingCountries>(RemainingCountries.class);
        List<RemainingCountries> reissueRegistrations = utils.importExcel("其余四国补发", file.getInputStream(), 0);

        ExcelUtil<SkuWeightquery> utilss = new ExcelUtil<SkuWeightquery>(SkuWeightquery.class);
        List<SkuWeightquery> skuWeightqueries = utilss.importExcel("SKU净重数据查询", file.getInputStream(), 0);


        message = reissueRegistrationService.importReissueRegistration(registrationList, updateSupport);
        message = iRemainingCountriesService.importRemainingCountries(reissueRegistrations, updateSupport);
        message = iSkuWeightqueryService.importSkuWeightquery(skuWeightqueries, updateSupport);
        return AjaxResult.success(message);
    }

    //导出 导入模板
    @RequiresPermissions("pms:registration:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<ReissueRegistration> util = new ExcelUtil<ReissueRegistration>(ReissueRegistration.class);
        return util.importTemplateExcel("补发登记");
    }
}
