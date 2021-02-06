package com.ruoyi.project.pms.badcommodity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BadCommodityPicture {
    private String picNo;
    private String path;
    private String size;
    private String ext;
    private String fileName;
}
