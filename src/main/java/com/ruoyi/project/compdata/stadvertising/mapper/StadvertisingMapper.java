package com.ruoyi.project.compdata.stadvertising.mapper;

import java.util.List;

import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.compdata.stadvertising.domain.Stadvertising;

/**
 * ST广告数据源Mapper接口
 * 
 * @author Beignet
 * @date 2021-01-25
 */
public interface StadvertisingMapper 
{
    /**
     * 查询ST广告数据源
     * 
     * @param id ST广告数据源ID
     * @return ST广告数据源
     */
    public Stadvertising selectStadvertisingById(Long id);

    /**
     * 查询ST广告数据源列表
     * 
     * @param stadvertising ST广告数据源
     * @return ST广告数据源集合
     */
    public List<Stadvertising> selectStadvertisingList(Stadvertising stadvertising);

    /**
     * 新增ST广告数据源
     * 
     * @param stadvertising ST广告数据源
     * @return 结果
     */
    public int insertStadvertising(Stadvertising stadvertising);

    /**
     * 修改ST广告数据源
     * 
     * @param stadvertising ST广告数据源
     * @return 结果
     */
    public int updateStadvertising(Stadvertising stadvertising);

    /**
     * 删除ST广告数据源
     * 
     * @param id ST广告数据源ID
     * @return 结果
     */
    public int deleteStadvertisingById(Long id);

    /**
     * 批量删除ST广告数据源
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStadvertisingByIds(String[] ids);

    Stadvertising selectStadvertisingMapperByOnlyCondition(Stadvertising stadvertising);

    int updateFinanceByOnlyCondition(Stadvertising stadvertising);

    int selectCount(Stadvertising stadvertising);
}
