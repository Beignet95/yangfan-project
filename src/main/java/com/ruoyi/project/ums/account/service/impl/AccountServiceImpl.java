package com.ruoyi.project.ums.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.framework.web.domain.CxSelect;
import com.ruoyi.project.ums.account.vo.AccountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.ums.account.mapper.AccountMapper;
import com.ruoyi.project.ums.account.domain.Account;
import com.ruoyi.project.ums.account.service.IAccountService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 账号Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-20
 */
@Service
public class AccountServiceImpl implements IAccountService 
{
    private static final Logger log = LoggerFactory.getLogger(Account.class);

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 查询账号
     * 
     * @param id 账号ID
     * @return 账号
     */
    @Override
    public Account selectAccountById(Long id)
    {
        return accountMapper.selectAccountById(id);
    }

    /**
     * 查询账号列表
     * 
     * @param account 账号
     * @return 账号
     */
    @Override
    public List<Account> selectAccountList(Account account)
    {
        return accountMapper.selectAccountList(account);
    }

    /**
     * 新增账号
     * 
     * @param account 账号
     * @return 结果
     */
    @Override
    public int insertAccount(Account account)
    {
        account.setCreateTime(DateUtils.getNowDate());
        return accountMapper.insertAccount(account);
    }

    /**
     * 修改账号
     * 
     * @param account 账号
     * @return 结果
     */
    @Override
    public int updateAccount(Account account)
    {
        account.setUpdateTime(DateUtils.getNowDate());
        return accountMapper.updateAccount(account);
    }

    /**
     * 删除账号对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAccountByIds(String ids)
    {
        return accountMapper.deleteAccountByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除账号信息
     * 
     * @param id 账号ID
     * @return 结果
     */
    @Override
    public int deleteAccountById(Long id)
    {
        return accountMapper.deleteAccountById(id);
    }

    /**
     * 导入账号
     *
     * @param accountList 账号List数据
     * @return 导入结果
     */
    @Override
    public String importAccount(List<Account> accountList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(accountList) || accountList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (Account account : accountList)
        {
            try
            {
                // 验证数据是否已经
                Account domain = accountMapper.selectAccountByOnlyCondition(account);
                if (domain==null)
                {
                    account.setCreateBy(operName);
                    account.setCreateTime(new Date());
                    this.insertAccount(account);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ account.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    account.setUpdateBy(operName);
                    account.setUpdateTime(new Date());
                    accountMapper.updateAccountByOnlyCondition(account);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + account.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + account.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + account.toString()+" 的数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     *  *  List<CxSelect> cxList = new ArrayList<CxSelect>();
     *      * CxSelect cxSelectTB = new CxSelect();
     *      *         cxSelectTB.setN("淘宝");
     *      *         cxSelectTB.setV("taobao");
     *      *         CxSelect cxSelectTm = new CxSelect();
     *      *         cxSelectTm.setN("天猫");
     *      *         cxSelectTm.setV("tm");
     *      *         CxSelect cxSelectJhs = new CxSelect();
     *      *         cxSelectJhs.setN("聚划算");
     *      *         cxSelectJhs.setV("jhs");
     *      *         List<CxSelect> tmList = new ArrayList<CxSelect>();
     *      *         tmList.add(cxSelectTm);
     *      *         tmList.add(cxSelectJhs);
     *      *         cxSelectTB.setS(tmList);
     * @return
     */
    @Override
    public String getJson4AccountSite() {
        List<AccountVo> voList = accountMapper.selectAccountVoList();
        Map<String,List<AccountVo>> groupMap = voList.stream().
                collect(Collectors.groupingBy(AccountVo::getAccount));
        List<CxSelect> cxList = new ArrayList<CxSelect>();
        for(String key:groupMap.keySet()){
            CxSelect fSelect = new CxSelect();
            fSelect.setN(key);
            fSelect.setV(key);

            List<AccountVo> accountVoList =  groupMap.get(key);
            List<CxSelect> seSelectList = new ArrayList<CxSelect>();
            for(AccountVo vo : accountVoList){
                CxSelect seSelect = new CxSelect();
                seSelect.setN(vo.getSite());
                seSelect.setV(vo.getSite());
                seSelectList.add(seSelect);
            }
            fSelect.setS(seSelectList);
            cxList.add(fSelect);
        }
        return JSONObject.toJSONString(cxList);
    }
}
