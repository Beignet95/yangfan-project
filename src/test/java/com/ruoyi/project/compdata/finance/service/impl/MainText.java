package com.ruoyi.project.compdata.finance.service.impl;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.ruoyi.common.utils.Arith;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.csv.CsvUtil;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.poi.ddf.EscherClientAnchorRecord;
import org.apache.poi.ddf.EscherRecord;
import org.apache.poi.hssf.record.EscherAggregate;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainText {
    public static void main(String[] args) throws ParseException {

        //InputStreamReader is = new InputStreamReader(new FileInputStream(
        // "C:\\Users\\Administrator\\Desktop\\测试\\英国CustomTransaction-source.csv"), "utf-8");

        try (Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\Administrator\\Desktop\\测试\\英国CustomTransaction-source.csv"));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] record;
            while ((record = csvReader.readNext()) != null) {
                System.out.println("User["+ String.join(", ", record) +"]");
            }
        } catch (IOException | CsvValidationException ex) {
            ex.printStackTrace();
        }

        testUTCtime();
    }
    public static void testUTCtime() throws ParseException {
        //字符串转Date
        //String stringDate = "Thu Oct 16 07:13:48 GMT 2015";//
        String stringDate = "02.01.2021 01:13:33 UTC";
        //SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy HH:mm:ss 'UTC'",Locale.US);
        Locale locale = new Locale("FR","");
        SimpleDateFormat sdf = new SimpleDateFormat("d.MM.yyyy HH:mm:ss 'UTC'",locale);
        Date date =sdf.parse(stringDate);
        System.out.println(date.toString());
    }


    public void testCVSIMPORT(){
        File csv = new File("C:\\Users\\Administrator\\Desktop\\开发需求汇总\\H4-EU 1月财务报表\\德国CustomTransaction.csv");  // CSV文件路径
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(csv));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        String line = "";
        String everyLine = "";
        try {
            List<String> allString = new ArrayList<>();
            while ((line = br.readLine()) != null)  //读取到的内容给line变量
            {
                everyLine = line;
                System.out.println(everyLine);
                allString.add(everyLine);
            }
            System.out.println("csv表格中所有行数："+allString.size());
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
