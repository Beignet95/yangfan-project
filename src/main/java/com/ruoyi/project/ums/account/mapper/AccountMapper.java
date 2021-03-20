package com.ruoyi.project.ums.account.mapper;

import java.util.List;
import com.ruoyi.project.ums.account.domain.Account;
import com.ruoyi.project.ums.account.vo.AccountVo;

/**
 * 账号Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-20
 */
public interface AccountMapper 
{
    /**
     * 查询账号
     * 
     * @param id 账号ID
     * @return 账号
     */
    public Account selectAccountById(Long id);

    /**
     * 查询账号列表
     * 
     * @param account 账号
     * @return 账号集合
     */
    public List<Account> selectAccountList(Account account);

    /**
     * 新增账号
     * 
     * @param account 账号
     * @return 结果
     */
    public int insertAccount(Account account);

    /**
     * 修改账号
     * 
     * @param account 账号
     * @return 结果
     */
    public int updateAccount(Account account);

    /**
     * 删除账号
     * 
     * @param id 账号ID
     * @return 结果
     */
    public int deleteAccountById(Long id);

    /**
     * 批量删除账号
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param Account ${subTable.functionName}
     * @return 结果
     */
    public int updateAccountByOnlyCondition(Account account);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param Account ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public Account selectAccountByOnlyCondition(Account account);

    List<AccountVo> selectAccountVoList();
}
