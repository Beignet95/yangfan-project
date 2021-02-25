package com.ruoyi.project.compdata.stadvertising.service;

import java.util.List;
import com.ruoyi.project.compdata.stadvertising.domain.Stadvertising;
import com.ruoyi.project.compdata.stadvertising.vo.KeywordAnalyVo;
import com.ruoyi.project.compdata.stadvertising.vo.KeywordEchartsVo;
import com.ruoyi.project.compdata.stadvertising.vo.StadvertisingAnalyVo;

/**
 * ST广告数据源Service接口
 * 
 * @author Beignet
 * @date 2021-01-25
 */
public interface IStadvertisingService 
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
     * 批量删除ST广告数据源
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStadvertisingByIds(String ids);

    /**
     * 删除ST广告数据源信息
     * 
     * @param id ST广告数据源ID
     * @return 结果
     */
    public int deleteStadvertisingById(Long id);

    String impStadvertising(List<Stadvertising> stadvertisingList, boolean updateSupport);

    List<StadvertisingAnalyVo> selectStadvertisingAnalyVoList(Stadvertising stadvertising);

    List<StadvertisingAnalyVo> selectStadvertisingAnalyVoList(StadvertisingAnalyVo stadvertising);

    int selectCount(Stadvertising stadvertising);

    List<KeywordAnalyVo> selectKeywordAnalysisData(KeywordAnalyVo keywordAnalyVo) throws IllegalAccessException;

    Integer selectTotalOrder(KeywordAnalyVo keywordAnalyVo);

    KeywordEchartsVo selectAdvertisingEchartsVo(Stadvertising stadvertising);
}
