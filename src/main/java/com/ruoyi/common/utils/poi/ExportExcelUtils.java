package com.ruoyi.common.utils.poi;

import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.web.domain.AjaxResult;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lwy
 * @Date: 2020/12/14
 * @Description:
 */
public class ExportExcelUtils {
    private String title; // 导出表格的表名

    private String[] rowName;// 导出表格的列名

    private List<Object[]> dataList = new ArrayList<Object[]>(); // 对象数组的List集合

    private HttpServletResponse response;

    // 传入要导入的数据
    public ExportExcelUtils(String title, String[] rowName, List<Object[]> dataList, HttpServletResponse response)
    {
        this.title = title;
        this.rowName = rowName;
        this.dataList = dataList;
        this.response = response;
    }

    // 导出数据
    public AjaxResult exportData(){
        String fileName=null;
        try{
            HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel对象
            HSSFSheet sheet = workbook.createSheet(title); // 创建表格
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);  // 行
            HSSFCell cellTiltle = rowm.createCell(0);  // 单元格
            // sheet样式定义
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook); // 头样式
            HSSFCellStyle style = this.getStyle(workbook);  // 单元格样式
            /**
             * 参数说明
             * 从0开始   第一行 第一列 都是从角标0开始
             * 行 列 行列    (0,0,0,5)  合并第一行 第一列  到第一行 第六列
             * 起始行，起始列，结束行，结束列
             *
             * new Region()  这个方法使过时的
             */
            // 合并第一行的所有列
            sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) (rowName.length - 1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);

            int columnNum = rowName.length;  // 表格列的长度
            HSSFRow rowRowName = sheet.createRow(1);  // 在第二行创建行
            HSSFCellStyle cells = workbook.createCellStyle();
            cells.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            rowRowName.setRowStyle(cells);

            // 循环 将列名放进去
            for (int i = 0; i < columnNum; i++){
                HSSFCell cellRowName = rowRowName.createCell((int) i);
                cellRowName.setCellType(CellType.STRING);// 单元格类型

                HSSFRichTextString text = new HSSFRichTextString(rowName[i]);  // 得到列的值
                cellRowName.setCellValue(text); // 设置列的值
                cellRowName.setCellStyle(columnTopStyle); // 样式
            }

            // 将查询到的数据设置到对应的单元格中
            for (int i = 0; i < dataList.size(); i++){
                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i + 2);//创建所需的行数
                row.setHeightInPoints(40);

                for (int j = 0; j < obj.length; j++){
                    HSSFCell cell = null;   //设置单元格的数据类型
                    if (j == 0) {
                        // 第一列设置为序号
                        cell = row.createCell(j, CellType.NUMERIC);
                        cell.setCellValue(i + 1);
                    }else{
                        cell = row.createCell(j, CellType.STRING);
                        if (!"".equals(obj[j]) && obj[j] != null){
                            if ((obj[j].toString()).contains("http") ){
                                cell.setCellValue("  ");
                                drawPictureInfoExcel(workbook, patriarch, obj[j].toString(), i + 2);//i+2代表当前的行
                            }else{
                                cell.setCellValue(obj[j].toString());
                            }
                            //设置单元格的值
                        }else{
                            cell.setCellValue("  ");
                        }

                    }
                    cell.setCellStyle(style); // 样式
                }
            }
            //  让列宽随着导出的列长自动适应
            sheet.setColumnWidth(0, 8 * 256);  //调整第一列宽度
            sheet.setColumnWidth(1, 15 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 15 * 256);
            sheet.setColumnWidth(4, 10 * 256);
            sheet.setColumnWidth(5, 25 * 256);
            sheet.setColumnWidth(6, 18 * 256);
            sheet.setColumnWidth(7, 20 * 256);
            sheet.setColumnWidth(8, 15 * 256);
            sheet.setColumnWidth(9, 18 * 256);
            sheet.setColumnWidth(10, 20 * 256);
            sheet.setColumnWidth(11, 18 * 256);
            sheet.setColumnWidth(12, 28 * 256);
            sheet.setColumnWidth(13, 20 * 256);
            sheet.setColumnWidth(14, 20 * 256);
            sheet.setColumnWidth(15, 20 * 256);
            sheet.setColumnWidth(16, 20 * 256);
            sheet.setColumnWidth(17, 15 * 256);
            sheet.setColumnWidth(18, 15 * 256);
            sheet.setColumnWidth(19, 15 * 256);
            sheet.setColumnWidth(20, 18 * 256);
            sheet.setColumnWidth(21, 20 * 256);
            sheet.setColumnWidth(22, 15 * 256);
            sheet.setColumnWidth(23, 15 * 256);
            sheet.setColumnWidth(24, 10 * 256);
            sheet.setColumnWidth(25, 10 * 256);
            sheet.setColumnWidth(26, 10 * 256);
            sheet.setColumnWidth(27, 21 * 256);
            sheet.setColumnWidth(28, 20 * 256);
            sheet.setColumnWidth(29, 20 * 256);
            sheet.setColumnWidth(30, 20 * 256);
            sheet.setColumnWidth(31, 20 * 256);
            sheet.setColumnWidth(32, 20 * 256);
            sheet.setColumnWidth(33, 15 * 256);
            sheet.setColumnWidth(34, 25 * 256);
            sheet.setColumnWidth(35, 20 * 256);
            sheet.setColumnWidth(36, 12 * 256);
//            sheet.setColumnWidth(37, 20 * 256);


            if (workbook != null){
                try{
                    // excel 表文件名
                    fileName = title + String.valueOf(System.currentTimeMillis()).substring(4, 13) + "..xlsx";
                    String fileName11 = URLEncoder.encode(fileName, "UTF-8");
                    String fileName12 = java.net.URLDecoder.decode(fileName11, "UTF-8");
                    String headStr = "attachment; filename=\"" + fileName12 + "\"";
                    response.setContentType("APPLICATION/OCTET-STREAM");
                    // response.setHeader("Content-Disposition", headStr);
                    response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String(fileName12.getBytes("gb2312"), "ISO8859-1"));
//                    OutputStream out = response.getOutputStream();
//                    workbook.write(out);
                    OutputStream out = new FileOutputStream(getAbsoluteFile(fileName));
                    workbook.write(out);
                    out.flush();
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return AjaxResult.success(fileName);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error();
        }
//        return fileName;
    }


    private void drawPictureInfoExcel(HSSFWorkbook wb, HSSFPatriarch patriarch, String pictureUrl, int rowIndex)
    {
        //rowIndex代表当前行
        try
        {
            if (pictureUrl != null)
            {
                URL url = new URL(pictureUrl);//获取人员照片的地址
                //打开链接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //设置请求方式为"GET"
                conn.setRequestMethod("GET");
                //超时响应时间为5秒
                conn.setConnectTimeout(5 * 1000);
                //通过输入流获取图片数据
                InputStream inStream = conn.getInputStream();
                //得到图片的二进制数据，以二进制封装得到数据，具有通用性
                byte[] data = readInputStream(inStream);
                //anchor主要用于设置图片的属性
                HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 1, rowIndex, (short) 1,
                        rowIndex);
                //Sets the anchor type （图片在单元格的位置）
                //0 = Move and size with Cells, 2 = Move but don't size with cells, 3 = Don't move or size with cells.
                anchor.setAnchorType(ClientAnchor.AnchorType.byId(1));
                patriarch.createPicture(anchor, wb.addPicture(data, HSSFWorkbook.PICTURE_TYPE_JPEG));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1)
        {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public HSSFCellStyle getStyle(HSSFWorkbook workbook)
    {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 9);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook)
    {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 10);
        //字体加粗
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setBold(true);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    public String getAbsoluteFile(String filename)
    {
        String downloadPath = RuoYiConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

}
