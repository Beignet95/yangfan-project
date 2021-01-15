package com.ruoyi.project.compdata.finance.mapper;

import java.util.List;
import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.compdata.finance.vo.FinanceAnalyVo;

/**
 * 财务数据Mapper接口
 * 
 * @author Beignet
 * @date 2021-01-13
 */
public interface FinanceMapper 
{
    /**
     * 查询财务数据
     * 
     * @param id 财务数据ID
     * @return 财务数据
     */
    public Finance selectFinanceById(Long id);

    /**
     * 查询财务数据列表
     * 
     * @param finance 财务数据
     * @return 财务数据集合
     */
    public List<Finance> selectFinanceList(Finance finance);

    /**
     * 新增财务数据
     * 
     * @param finance 财务数据
     * @return 结果
     */
    public int insertFinance(Finance finance);

    /**
     * 修改财务数据
     * 
     * @param finance 财务数据
     * @return 结果
     */
    public int updateFinance(Finance finance);

    /**
     * 删除财务数据
     * 
     * @param id 财务数据ID
     * @return 结果
     */
    public int deleteFinanceById(Long id);

    /**
     * 批量删除财务数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFinanceByIds(String[] ids);

    Finance selectUserByOnlyCondition(Finance finance);

    List<FinanceAnalyVo> selectFinanceAgg(Finance finance);

    int updateFinanceByOnlyCondition(Finance finance);
}
