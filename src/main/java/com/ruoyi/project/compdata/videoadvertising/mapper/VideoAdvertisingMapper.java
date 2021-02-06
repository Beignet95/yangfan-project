package com.ruoyi.project.compdata.videoadvertising.mapper;

import java.util.List;
import com.ruoyi.project.compdata.videoadvertising.domain.VideoAdvertising;

/**
 * 视频广告数据源Mapper接口
 * 
 * @author Beignet
 * @date 2021-01-29
 */
public interface VideoAdvertisingMapper 
{
    /**
     * 查询视频广告数据源
     * 
     * @param id 视频广告数据源ID
     * @return 视频广告数据源
     */
    public VideoAdvertising selectVideoAdvertisingById(Long id);

    /**
     * 查询视频广告数据源列表
     * 
     * @param videoAdvertising 视频广告数据源
     * @return 视频广告数据源集合
     */
    public List<VideoAdvertising> selectVideoAdvertisingList(VideoAdvertising videoAdvertising);

    /**
     * 新增视频广告数据源
     * 
     * @param videoAdvertising 视频广告数据源
     * @return 结果
     */
    public int insertVideoAdvertising(VideoAdvertising videoAdvertising);

    /**
     * 修改视频广告数据源
     * 
     * @param videoAdvertising 视频广告数据源
     * @return 结果
     */
    public int updateVideoAdvertising(VideoAdvertising videoAdvertising);

    /**
     * 删除视频广告数据源
     * 
     * @param id 视频广告数据源ID
     * @return 结果
     */
    public int deleteVideoAdvertisingById(Long id);

    /**
     * 批量删除视频广告数据源
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteVideoAdvertisingByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param VideoAdvertising ${subTable.functionName}
     * @return 结果
     */
    public int updateVideoAdvertisingByOnlyCondition(VideoAdvertising videoAdvertising);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param VideoAdvertising ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public VideoAdvertising selectVideoAdvertisingByOnlyCondition(VideoAdvertising videoAdvertising);
}
