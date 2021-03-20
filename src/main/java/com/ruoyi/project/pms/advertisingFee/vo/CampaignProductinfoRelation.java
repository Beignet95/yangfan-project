package com.ruoyi.project.pms.advertisingFee.vo;

import com.ruoyi.project.pms.advertisingFee.domain.AdvertisingFee;
import com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation;
import lombok.Data;

@Data
public class CampaignProductinfoRelation extends ProductinfoRelation {
    private String campaign;//广告词
}
