package com.ruoyi.common.utils.uuid;

import java.math.BigDecimal;

public class MathUtil {
    /**
     * 将Float型数据转为百分比数值，且只保留两位小数
     * @param floatNum
     * @return
     */
    public static Float float2PercentNum(Float floatNum){
        if(floatNum==null) return 0f;
        BigDecimal b  =   new BigDecimal(floatNum*100);
        float   f   =  b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f;
    }

    /**
     * 将Float型数据转为百分比数值，且只保留两位小数
     * @param floatNum
     * @return
     */
    public static Float float2PercentNum(Float floatNum,int decimalNum){
        if(floatNum==null) return 0f;
        BigDecimal b  =   new BigDecimal(floatNum*100);
        float   f   =  b.setScale(decimalNum, BigDecimal.ROUND_HALF_UP).floatValue();
        return f;
    }
}
