package com.ruoyi.project.ums.account.vo;

import com.ruoyi.project.ums.site.domain.Site;
import lombok.Data;

/**
 * 账号与站点的关系实体
 */
@Data
public class AccountVo extends Site {
    private String account;
}
