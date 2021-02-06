package com.ruoyi.project.system.net.zwIpFiter.mapper;

import java.util.List;

import com.ruoyi.project.system.net.zwIpFiter.domain.SysZwIpFilter;

/**
 * ZwIpFilterMapper接口
 * 
 * @author Beignet
 * @date 2021-01-30
 */
public interface SysZwIpFilterMapper 
{
    /**
     * 查询ZwIpFilter
     * 
     * @param id ZwIpFilterID
     * @return ZwIpFilter
     */
    public SysZwIpFilter selectSysZwIpFilterById(Long id);

    /**
     * 查询ZwIpFilter列表
     * 
     * @param sysZwIpFilter ZwIpFilter
     * @return ZwIpFilter集合
     */
    public List<SysZwIpFilter> selectSysZwIpFilterList(SysZwIpFilter sysZwIpFilter);

    /**
     * 新增ZwIpFilter
     * 
     * @param sysZwIpFilter ZwIpFilter
     * @return 结果
     */
    public int insertSysZwIpFilter(SysZwIpFilter sysZwIpFilter);

    /**
     * 修改ZwIpFilter
     * 
     * @param sysZwIpFilter ZwIpFilter
     * @return 结果
     */
    public int updateSysZwIpFilter(SysZwIpFilter sysZwIpFilter);

    /**
     * 删除ZwIpFilter
     * 
     * @param id ZwIpFilterID
     * @return 结果
     */
    public int deleteSysZwIpFilterById(Long id);

    /**
     * 批量删除ZwIpFilter
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysZwIpFilterByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param SysZwIpFilter ${subTable.functionName}
     * @return 结果
     */
    public int updateSysZwIpFilterByOnlyCondition(SysZwIpFilter sysZwIpFilter);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param SysZwIpFilter ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public SysZwIpFilter selectSysZwIpFilterByOnlyCondition(SysZwIpFilter sysZwIpFilter);
}
