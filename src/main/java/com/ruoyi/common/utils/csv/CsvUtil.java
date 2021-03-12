package com.ruoyi.common.utils.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvBindByPosition;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.reflect.ReflectUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class CsvUtil<T> {
    /**
     * 实体对象
     */
    public Class<T> clazz;

    public CsvUtil(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    /**
     * 导出类型
     */
    private Excel.Type type;

    public List<T> importCvs(InputStream inputStream, int noImportRowNum) throws Exception
    {
        List<T> list = new ArrayList<T>();
        this.type = Excel.Type.IMPORT;

        InputStreamReader is = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(is);
        String[] cols;
        int rowNum=0;
        Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();

        while ((cols = csvReader.readNext()) != null)  //读取到的内容给line变量
        {
            if(noImportRowNum==rowNum) {
                // 定义一个map用于存放excel列的序号和field.
                //Map<String, Integer> cellMap = new HashMap<String, Integer>();
                Map<Integer, String> cellMap = new HashMap<Integer, String>();
                // 获取表头
                for (int i = 0; i < cols.length; i++) {
                    String cell = cols[i];
                    if (StringUtils.isNotNull(cell)) {
                        String value = cell;
                        //cellMap.put(value, i);
                        cellMap.put(i, value);
                    } else {
                        //cellMap.put(null, i);
                        cellMap.put(i, null);
                    }
                }
                // 有数据时才处理 得到类的所有field.
                Field[] allFields = clazz.getDeclaredFields();
                // 定义一个map用于存放列的序号和field.
                for (int col = 0; col < allFields.length; col++) {
                    Field field = allFields[col];
                    //Excel attr = field.getAnnotation(Excel.class);
//                    if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type)) {
//                        // 设置类的私有字段属性可访问.
//                        field.setAccessible(true);
//                        Integer column = cellMap.get(attr.name());
//                        if (column != null) {
//                            fieldsMap.put(column, field);
//                        }
//                    }
                    CsvBindByPosition position = field.getAnnotation(CsvBindByPosition.class);
                    if(position!=null){
                        field.setAccessible(true);
                        Integer column = position.position();
                        if (column != null) {
                            fieldsMap.put(column, field);
                        }

                    }

                }
            }

                //正式读取数据
                //for (int i = noImportRowNum+1; i < cols.length; i++)
                if(rowNum>noImportRowNum)
                {
                    T entity = null;
                    for (Map.Entry<Integer, Field> entry : fieldsMap.entrySet())
                    {
                        Object val = cols[entry.getKey()];
                        entity = (entity == null ? clazz.newInstance() : entity);
                        // 从map中得到对应列的field.
                        Field field = fieldsMap.get(entry.getKey());
                        // 取得类型,并根据对象类型设置值.
                        Class<?> fieldType = field.getType();
                        if (String.class == fieldType)
                        {
                            String s = Convert.toStr(val);
                            if (StringUtils.endsWith(s, ".0"))
                            {
                                val = StringUtils.substringBefore(s, ".0");
                            }
                            else
                            {
                                String dateFormat = field.getAnnotation(Excel.class).dateFormat();
                                String dateType = field.getAnnotation(Excel.class).dateType();
                                if(StringUtils.isNotEmpty(dateType)){
                                    String valStr = val.toString();
                                    if(valStr.length()>0){
                                        val = DateUtils.parseIntegerToDateStr(dateFormat,Integer.valueOf(valStr.toString()));
                                    }
                                }else if (StringUtils.isNotEmpty(dateFormat))
                                {
                                    val = DateUtils.parseDateToStr(dateFormat, (Date) val);
                                }
                                else
                                {
                                    val = Convert.toStr(val);
                                }
                            }
                        }
                        else if ((Integer.TYPE == fieldType || Integer.class == fieldType) && StringUtils.isNumeric(Convert.toStr(val)))
                        {
                            val = Convert.toInt(val);
                        }
                        else if (Long.TYPE == fieldType || Long.class == fieldType)
                        {
                            val = Convert.toLong(val);
                        }
                        else if (Double.TYPE == fieldType || Double.class == fieldType)
                        {
                            val = val.toString().replace(",",".");
                            val = Convert.toDouble(val);
                        }
                        else if (Float.TYPE == fieldType || Float.class == fieldType)
                        {
                            val = val.toString().replace(",",".");
                            val = Convert.toFloat(val);
                        }
                        else if (BigDecimal.class == fieldType)
                        {
                            val = val.toString().replace(",",".");
                            val = Convert.toBigDecimal(val);
                        }
                        else if (Date.class == fieldType)
                        {
                            String dateFormat = field.getAnnotation(Excel.class).dateFormat();
                            if(StringUtils.isNotEmpty(dateFormat)){
                                val = DateUtils.parseDate(val);
                            }
                            else if (val instanceof String)
                            {
                                val = DateUtils.parseUTCDate4CSV(val);
                            }
                            else if (val instanceof Double)
                            {
                                val = DateUtil.getJavaDate((Double) val);
                            }
                        }
                        else if (Boolean.TYPE == fieldType || Boolean.class == fieldType)
                        {
                            val = Convert.toBool(val, false);
                        }
                        if (StringUtils.isNotNull(fieldType))
                        {
                            Excel attr = field.getAnnotation(Excel.class);
                            String propertyName = field.getName();
                            if (StringUtils.isNotEmpty(attr.targetAttr()))
                            {
                                propertyName = field.getName() + "." + attr.targetAttr();
                            }
                            else if (StringUtils.isNotEmpty(attr.readConverterExp()))
                            {
                                val = ExcelUtil.reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
                            }
                            else if (StringUtils.isNotEmpty(attr.dictType()))
                            {
                                val = ExcelUtil.reverseDictByExp(Convert.toStr(val), attr.dictType(), attr.separator());
                            }
                            ReflectUtils.invokeSetter(entity, propertyName, val);
                        }
                    }
                    list.add(entity);
                }
            rowNum++;
            }
        return  list;
    }
}
