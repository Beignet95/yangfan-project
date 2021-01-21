package com.ruoyi.project.compdata.advertising.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 广告分析页面条件展现实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisingAnalySearchVo {
    List<String> types;
    List<String> storeCodes;
    List<String> skus;
    List<String> Asins;
    String selectType;
    String selectStoreCode;
    String selectSku;
    String selectAsin;
    String changeSelect;
}
