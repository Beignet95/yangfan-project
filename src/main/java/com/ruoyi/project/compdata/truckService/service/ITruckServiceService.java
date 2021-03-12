package com.ruoyi.project.compdata.truckService.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ruoyi.project.compdata.truckService.domain.TruckService;

/**
 * 卡车费用Service接口
 * 
 * @author Beignet
 * @date 2021-03-06
 */
public interface ITruckServiceService 
{
    /**
     * 查询卡车费用
     * 
     * @param id 卡车费用ID
     * @return 卡车费用
     */
    public TruckService selectTruckServiceById(Long id);

    /**
     * 查询卡车费用列表
     * 
     * @param truckService 卡车费用
     * @return 卡车费用集合
     */
    public List<TruckService> selectTruckServiceList(TruckService truckService);

    /**
     * 新增卡车费用
     * 
     * @param truckService 卡车费用
     * @return 结果
     */
    public int insertTruckService(TruckService truckService);

    /**
     * 修改卡车费用
     * 
     * @param truckService 卡车费用
     * @return 结果
     */
    public int updateTruckService(TruckService truckService);

    /**
     * 批量删除卡车费用
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTruckServiceByIds(String ids);

    /**
     * 删除卡车费用信息
     * 
     * @param id 卡车费用ID
     * @return 结果
     */
    public int deleteTruckServiceById(Long id);

    /**
     * 导入卡车费用
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    String importTruckService(List<TruckService> truckServiceList, boolean updateSupport, Long truckRecordId);

    /**
     * 获取最近一个月的卡车服务费
     * @return
     */
    Map<String, Long> getTructServiceFeeMap(TruckService truckService);
}
