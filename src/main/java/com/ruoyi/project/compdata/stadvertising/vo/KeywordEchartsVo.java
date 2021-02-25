package com.ruoyi.project.compdata.stadvertising.vo;

import lombok.Data;

@Data
public class KeywordEchartsVo {
    private String[] months;
    private Integer[] totalOrders;
    private Float[] orderRates;
    private Float[] cvrs;
    private Float[] cpcs;
    private Integer[] Impressionses;
    private Integer[] clicks;
    private Float[] ctrs;
}
