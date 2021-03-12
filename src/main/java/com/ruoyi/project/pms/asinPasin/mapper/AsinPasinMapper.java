package com.ruoyi.project.pms.asinPasin.mapper;

import java.util.List;
import com.ruoyi.project.pms.asinPasin.domain.AsinPasin;

/**
 * ASIN与父ASIN关系Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-08
 */
public interface AsinPasinMapper 
{
    /**
     * 查询ASIN与父ASIN关系
     * 
     * @param id ASIN与父ASIN关系ID
     * @return ASIN与父ASIN关系
     */
    public AsinPasin selectAsinPasinById(Long id);

    /**
     * 查询ASIN与父ASIN关系列表
     * 
     * @param asinPasin ASIN与父ASIN关系
     * @return ASIN与父ASIN关系集合
     */
    public List<AsinPasin> selectAsinPasinList(AsinPasin asinPasin);

    /**
     * 新增ASIN与父ASIN关系
     * 
     * @param asinPasin ASIN与父ASIN关系
     * @return 结果
     */
    public int insertAsinPasin(AsinPasin asinPasin);

    /**
     * 修改ASIN与父ASIN关系
     * 
     * @param asinPasin ASIN与父ASIN关系
     * @return 结果
     */
    public int updateAsinPasin(AsinPasin asinPasin);

    /**
     * 删除ASIN与父ASIN关系
     * 
     * @param id ASIN与父ASIN关系ID
     * @return 结果
     */
    public int deleteAsinPasinById(Long id);

    /**
     * 批量删除ASIN与父ASIN关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAsinPasinByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param AsinPasin ${subTable.functionName}
     * @return 结果
     */
    public int updateAsinPasinByOnlyCondition(AsinPasin asinPasin);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param AsinPasin ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public AsinPasin selectAsinPasinByOnlyCondition(AsinPasin asinPasin);
}
