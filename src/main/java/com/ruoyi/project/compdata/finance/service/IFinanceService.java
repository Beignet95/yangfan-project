package com.ruoyi.project.compdata.finance.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.compdata.finance.vo.FinanceAnalyParamVo;
import com.ruoyi.project.compdata.finance.vo.FinanceEchartsVo;
import com.ruoyi.project.compdata.finance.vo.TypeProfitAnalyVo;
import com.ruoyi.project.compdata.finance.vo.TypeProfitEchartsVo;

/**
 * 财务数据Service接口
 * 
 * @author Beignet
 * @date 2021-01-13
 */
public interface IFinanceService 
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
     * 批量删除财务数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFinanceByIds(String ids);

    /**
     * 删除财务数据信息
     * 
     * @param id 财务数据ID
     * @return 结果
     */
    public int deleteFinanceById(Long id);

    String importFinance(List<Finance> financeList, boolean updateSupport);

    FinanceEchartsVo selectFinanceEchartsVo(Finance finance) throws IllegalAccessException;

    Map selectAnalySearch(FinanceAnalyParamVo financeAnalyParamVo);

    List<TypeProfitAnalyVo> selectTypeProfitAnalyVoList(String curMonthStr);

    TypeProfitEchartsVo selectTypeProfitEchartsVo(String curMonthStr);
}
