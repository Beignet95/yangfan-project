package com.ruoyi.project.compdata.truckService.mapper;

import java.util.List;
import com.ruoyi.project.compdata.truckService.domain.TruckService;

/**
 * 卡车费用Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-06
 */
public interface TruckServiceMapper 
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
     * 删除卡车费用
     * 
     * @param id 卡车费用ID
     * @return 结果
     */
    public int deleteTruckServiceById(Long id);

    /**
     * 批量删除卡车费用
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTruckServiceByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param TruckService ${subTable.functionName}
     * @return 结果
     */
    public int updateTruckServiceByOnlyCondition(TruckService truckService);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param TruckService ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public TruckService selectTruckServiceByOnlyCondition(TruckService truckService);
}
