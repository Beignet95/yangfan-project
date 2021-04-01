package com.ruoyi.project.ums.site.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.ums.site.mapper.SiteMapper;
import com.ruoyi.project.ums.site.domain.Site;
import com.ruoyi.project.ums.site.service.ISiteService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 站点Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-20
 */
@Service
public class SiteServiceImpl implements ISiteService 
{
    private static final Logger log = LoggerFactory.getLogger(Site.class);

    @Autowired
    private SiteMapper siteMapper;

    /**
     * 查询站点
     * 
     * @param id 站点ID
     * @return 站点
     */
    @Override
    public Site selectSiteById(Long id)
    {
        return siteMapper.selectSiteById(id);
    }

    /**
     * 查询站点列表
     * 
     * @param site 站点
     * @return 站点
     */
    @Override
    public List<Site> selectSiteList(Site site)
    {
        return siteMapper.selectSiteList(site);
    }

    /**
     * 新增站点
     * 
     * @param site 站点
     * @return 结果
     */
    @Override
    public int insertSite(Site site)
    {
        site.setCreateTime(DateUtils.getNowDate());
        return siteMapper.insertSite(site);
    }

    /**
     * 修改站点
     * 
     * @param site 站点
     * @return 结果
     */
    @Override
    public int updateSite(Site site)
    {
        site.setCreateBy(ShiroUtils.getLoginName());
        site.setUpdateTime(DateUtils.getNowDate());
        return siteMapper.updateSite(site);
    }

    /**
     * 删除站点对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSiteByIds(String ids)
    {
        return siteMapper.deleteSiteByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除站点信息
     * 
     * @param id 站点ID
     * @return 结果
     */
    @Override
    public int deleteSiteById(Long id)
    {
        return siteMapper.deleteSiteById(id);
    }

    /**
     * 导入站点
     *
     * @param siteList 站点List数据
     * @return 导入结果
     */
    @Override
    public String importSite(List<Site> siteList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(siteList) || siteList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (Site site : siteList)
        {
            try
            {
                // 验证数据是否已经
                Site domain = siteMapper.selectSiteByOnlyCondition(site);
                if (domain==null)
                {
                    site.setCreateBy(operName);
                    site.setCreateTime(new Date());
                    this.insertSite(site);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ site.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    site.setUpdateBy(operName);
                    site.setUpdateTime(new Date());
                    siteMapper.updateSiteByOnlyCondition(site);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + site.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + site.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + site.toString()+" 的数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
