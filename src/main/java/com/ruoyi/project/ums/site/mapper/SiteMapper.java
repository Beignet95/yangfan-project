package com.ruoyi.project.ums.site.mapper;

import java.util.List;
import com.ruoyi.project.ums.site.domain.Site;

/**
 * 站点Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-20
 */
public interface SiteMapper 
{
    /**
     * 查询站点
     * 
     * @param id 站点ID
     * @return 站点
     */
    public Site selectSiteById(Long id);

    /**
     * 查询站点列表
     * 
     * @param site 站点
     * @return 站点集合
     */
    public List<Site> selectSiteList(Site site);

    /**
     * 新增站点
     * 
     * @param site 站点
     * @return 结果
     */
    public int insertSite(Site site);

    /**
     * 修改站点
     * 
     * @param site 站点
     * @return 结果
     */
    public int updateSite(Site site);

    /**
     * 删除站点
     * 
     * @param id 站点ID
     * @return 结果
     */
    public int deleteSiteById(Long id);

    /**
     * 批量删除站点
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSiteByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param Site ${subTable.functionName}
     * @return 结果
     */
    public int updateSiteByOnlyCondition(Site site);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param Site ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public Site selectSiteByOnlyCondition(Site site);
}
