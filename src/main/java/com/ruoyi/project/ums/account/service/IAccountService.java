package com.ruoyi.project.ums.account.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.ums.account.domain.Account;

/**
 * 账号Service接口
 * 
 * @author Beignet
 * @date 2021-03-20
 */
public interface IAccountService 
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
     * 批量删除账号
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAccountByIds(String ids);

    /**
     * 删除账号信息
     * 
     * @param id 账号ID
     * @return 结果
     */
    public int deleteAccountById(Long id);

    /**
     * 导入账号
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importAccount(List<Account> accountList, boolean isUpdateSupport);


    /**
     * 获取账号与站点的关系
     * @return
     */
    String getJson4AccountSite();

    String getJson4AllAccountSite();
}
