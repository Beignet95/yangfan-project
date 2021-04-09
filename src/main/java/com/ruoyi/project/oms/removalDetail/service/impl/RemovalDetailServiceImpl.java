package com.ruoyi.project.oms.removalDetail.service.impl;

import java.util.*;

import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;
import com.ruoyi.project.compdata.historyoperate.service.IHistoryOperateService;
import com.ruoyi.project.pms.asinMsku.domain.AsinMsku;
import com.ruoyi.project.pms.asinMsku.service.IAsinMskuService;
import com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation;
import com.ruoyi.project.pms.productinfoReation.service.IProductinfoRelationService;
import com.ruoyi.project.pms.productinfoReation.vo.MskuProductinfoRelationVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.oms.removalDetail.mapper.RemovalDetailMapper;
import com.ruoyi.project.oms.removalDetail.domain.RemovalDetail;
import com.ruoyi.project.oms.removalDetail.service.IRemovalDetailService;
import com.ruoyi.common.utils.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataUnit;

import javax.swing.*;

/**
 * 移除明细Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-08
 */
@Service
public class RemovalDetailServiceImpl implements IRemovalDetailService {
    private static final Logger log = LoggerFactory.getLogger(RemovalDetail.class);

    private final String HISTORY_OPERARE_PREFIX = "imp:removal:r:";

    @Autowired
    private RemovalDetailMapper removalDetailMapper;

    @Autowired
    private IHistoryOperateService historyOperateService;

    /**
     * 查询移除明细
     *
     * @param id 移除明细ID
     * @return 移除明细
     */
    @Override
    public RemovalDetail selectRemovalDetailById(Long id) {
        return removalDetailMapper.selectRemovalDetailById(id);
    }

    /**
     * 查询移除明细列表
     *
     * @param removalDetail 移除明细
     * @return 移除明细
     */
    @Override
    public List<RemovalDetail> selectRemovalDetailList(RemovalDetail removalDetail) {
        return removalDetailMapper.selectRemovalDetailList(removalDetail);
    }

    /**
     * 新增移除明细
     *
     * @param removalDetail 移除明细
     * @return 结果
     */
    @Override
    public int insertRemovalDetail(RemovalDetail removalDetail) {
        removalDetail.setCreateTime(DateUtils.getNowDate());
        return removalDetailMapper.insertRemovalDetail(removalDetail);
    }

    /**
     * 修改移除明细
     *
     * @param removalDetail 移除明细
     * @return 结果
     */
    @Override
    public int updateRemovalDetail(RemovalDetail removalDetail) {
        removalDetail.setUpdateTime(DateUtils.getNowDate());
        return removalDetailMapper.updateRemovalDetail(removalDetail);
    }

    /**
     * 删除移除明细对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRemovalDetailByIds(String ids) {
        return removalDetailMapper.deleteRemovalDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除移除明细信息
     *
     * @param id 移除明细ID
     * @return 结果
     */
    @Override
    public int deleteRemovalDetailById(Long id) {
        return removalDetailMapper.deleteRemovalDetailById(id);
    }

    /**
     * 导入移除明细
     *
     * @param removalDetailList 移除明细List数据
     * @return 导入结果
     */
    @Override
    @Transactional
    public String importRemovalDetail(List<RemovalDetail> removalDetailList, boolean isUpdateSupport, String account, Date month) {
        if (StringUtils.isNull(removalDetailList) || removalDetailList.size() == 0) {
            throw new BusinessException("导入数据不能为空！");
        }

        HistoryOperate ho = checkAndInterceptImp(account, month);//检查是否有做个导入操作
        Map<String, MskuProductinfoRelationVo> mskuPrlMap = checkAndGetPrlMap(removalDetailList);

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (RemovalDetail removalDetail : removalDetailList) {
            try {
                if (removalDetail.getRemovalFee() == null) continue;//移除费为空时，则不作操作
                MskuProductinfoRelationVo mskuPrl = mskuPrlMap.get(removalDetail.getSku());

                removalDetail.setPrincipal(mskuPrl.getPrincipal());
                removalDetail.setSpu(mskuPrl.getType());
                removalDetail.setStandardSku(mskuPrl.getSku());
                removalDetail.setAccount(account);
                removalDetail.setMonth(month);
                removalDetail.setCreateBy(operName);
                removalDetail.setCreateTime(new Date());
                this.insertRemovalDetail(removalDetail);
                successNum++;
                successMsg.append("<br/>" + successNum + "、" + removalDetail.toString() + " 的数据导入成功");
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + removalDetail.toString() + " 的数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            historyOperateService.insertHistoryOperate(ho);//插入导入记录
        }
        return successMsg.toString();
    }

    @Override
    @Transactional
    public int unlockData(Date month, String account) {
        String monstr = DateUtils.parseDateToStr("yyyy-MM",month);
        if(historyOperateService.deleteHistoryOperateByOperateCode(getHistoryCode(account,monstr))>0){
            return removalDetailMapper.deleteRemovalDetailByTypeAndAccount(month,account);
        }else return -1;
    }

    private HistoryOperate checkAndInterceptImp(String account, Date month) {
        String monthStr = DateUtils.parseDateToStr("yyyy-MM", month);
        String history_operate_code = getHistoryCode(account,monthStr);
        HistoryOperate ho = new HistoryOperate();
        ho.setRepeatCode(history_operate_code);
        List<HistoryOperate> hoRes = historyOperateService.selectHistoryOperateList(ho);
        if (hoRes.size() > 0) throw new BusinessException(monthStr + "月份，账号为"
                + account + "的仓储数据导入操作已被锁定！你可能已经导入过数据了！");
        return ho;
    }

    private String getHistoryCode(String account, String monthStr) {
        return  HISTORY_OPERARE_PREFIX + account + ":" + monthStr;
    }

    @Autowired
    IAsinMskuService asinMskuService;
    @Autowired
    IProductinfoRelationService productinfoRelationService;

    /**
     * 校验是否存在MSKU与ASIN  ASIN与产品信息的关系
     * 有：返回MSKU与产品关系Map
     * 没有：抛出异常，提示缺少对应关系
     *
     * @param removalDetailList
     * @return
     */
    private Map<String, MskuProductinfoRelationVo> checkAndGetPrlMap(List<RemovalDetail> removalDetailList) {
        if (removalDetailList.get(0) == null) throw new BusinessException("报表格式异常！请下载模板进行对照！");
        List<AsinMsku> asinMskuList = asinMskuService.selectAsinMskuList(null);
        Map<String, MskuProductinfoRelationVo> mskuPrlMap = productinfoRelationService.getMskuProductinfoRelationVoMap(null);
        Map<String, String> mskuAsinMap = asinMskuList.stream().collect(Collectors.toMap(AsinMsku::getMsku, AsinMsku::getAsin));
        Set<String> cot = new LinkedHashSet();
        Set<String> cot2 = new LinkedHashSet();
        for (RemovalDetail removalDetail : removalDetailList) {
            String msku = removalDetail.getSku();
            if (StringUtils.isNotEmpty(msku) && !mskuAsinMap.containsKey(msku)) cot.add(msku);
            if (StringUtils.isNotEmpty(msku) && !mskuPrlMap.containsKey(msku)) cot2.add(msku);
        }
        StringBuilder warnMsg = new StringBuilder();
        if (cot.size() > 0) {
            warnMsg.append("<br/>以下MSKU缺少与ASIN的关系！");
            for (String msku : cot) {
                warnMsg.append("<br/>" + msku);
            }
            if (cot2.size() > 0) {
                warnMsg.append("<br/>以下MSKU关联的ASIN缺少关系！");
                for (String msku : cot) warnMsg.append("<br/>" + msku);
            }
            if (warnMsg.length() > 0) throw new BusinessException(warnMsg.toString());
        }
        return mskuPrlMap;
    }
}