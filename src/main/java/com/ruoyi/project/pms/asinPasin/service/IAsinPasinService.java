package com.ruoyi.project.pms.asinPasin.service;

import java.util.List;
import com.ruoyi.project.pms.asinPasin.domain.AsinPasin;

/**
 * ASIN与父ASIN关系Service接口
 * 
 * @author Beignet
 * @date 2021-03-08
 */
public interface IAsinPasinService 
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
     * 批量删除ASIN与父ASIN关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAsinPasinByIds(String ids);

    /**
     * 删除ASIN与父ASIN关系信息
     * 
     * @param id ASIN与父ASIN关系ID
     * @return 结果
     */
    public int deleteAsinPasinById(Long id);

    /**
     * 导入ASIN与父ASIN关系
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importAsinPasin(List<AsinPasin> asinPasinList, boolean isUpdateSupport);


}
