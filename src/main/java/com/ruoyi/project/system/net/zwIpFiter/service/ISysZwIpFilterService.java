package com.ruoyi.project.system.net.zwIpFiter.service;

import java.util.List;
import com.ruoyi.project.system.net.zwIpFiter.domain.SysZwIpFilter;

/**
 * ZwIpFilterService接口
 * 
 * @author Beignet
 * @date 2021-01-30
 */
public interface ISysZwIpFilterService 
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
     * 批量删除ZwIpFilter
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysZwIpFilterByIds(String ids);

    /**
     * 删除ZwIpFilter信息
     * 
     * @param id ZwIpFilterID
     * @return 结果
     */
    public int deleteSysZwIpFilterById(Long id);

    /**
     * 导入ZwIpFilter
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importSysZwIpFilter(List<SysZwIpFilter> sysZwIpFilterList, boolean isUpdateSupport);


}
