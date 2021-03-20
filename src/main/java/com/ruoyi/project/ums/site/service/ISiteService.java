package com.ruoyi.project.ums.site.service;

import java.util.List;
import com.ruoyi.project.ums.site.domain.Site;

/**
 * 站点Service接口
 * 
 * @author Beignet
 * @date 2021-03-20
 */
public interface ISiteService 
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
     * 批量删除站点
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSiteByIds(String ids);

    /**
     * 删除站点信息
     * 
     * @param id 站点ID
     * @return 结果
     */
    public int deleteSiteById(Long id);

    /**
     * 导入站点
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importSite(List<Site> siteList, boolean isUpdateSupport);


}
