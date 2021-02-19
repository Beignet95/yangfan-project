package com.ruoyi.project.pms.relation.service.impl;

import java.util.List;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.ListUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.compdata.advertising.domain.Advertising;
import com.ruoyi.project.compdata.advertising.service.IAdvertisingService;
import com.ruoyi.project.system.user.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pms.relation.mapper.AsinTypeRelationMapper;
import com.ruoyi.project.pms.relation.domain.AsinTypeRelation;
import com.ruoyi.project.pms.relation.service.IAsinTypeRelationService;
import com.ruoyi.common.utils.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * ASIN与型号关联Service业务层处理
 * 
 * @author Beignet
 * @date 2021-01-22
 */
@Service
public class AsinTypeRelationServiceImpl implements IAsinTypeRelationService 
{
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private AsinTypeRelationMapper asinTypeRelationMapper;

    @Autowired
    private IAdvertisingService advertisingService;

    /**
     * 查询ASIN与型号关联
     * 
     * @param id ASIN与型号关联ID
     * @return ASIN与型号关联
     */
    @Override
    public AsinTypeRelation selectAsinTypeRelationById(Long id)
    {
        return asinTypeRelationMapper.selectAsinTypeRelationById(id);
    }

    /**
     * 查询ASIN与型号关联列表
     * 
     * @param asinTypeRelation ASIN与型号关联
     * @return ASIN与型号关联
     */
    @Override
    public List<AsinTypeRelation> selectAsinTypeRelationList(AsinTypeRelation asinTypeRelation)
    {
        return asinTypeRelationMapper.selectAsinTypeRelationList(asinTypeRelation);
    }

    /**
     * 新增ASIN与型号关联
     * 
     * @param asinTypeRelation ASIN与型号关联
     * @return 结果
     */
    @Override
    public int insertAsinTypeRelation(AsinTypeRelation asinTypeRelation)
    {
        return asinTypeRelationMapper.insertAsinTypeRelation(asinTypeRelation);
    }

    /**
     * 修改ASIN与型号关联
     * 
     * @param asinTypeRelation ASIN与型号关联
     * @return 结果
     */
    @Override
    public int updateAsinTypeRelation(AsinTypeRelation asinTypeRelation)
    {
        return asinTypeRelationMapper.updateAsinTypeRelation(asinTypeRelation);
    }

    /**
     * 删除ASIN与型号关联对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAsinTypeRelationByIds(String ids)
    {
        return asinTypeRelationMapper.deleteAsinTypeRelationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除ASIN与型号关联信息
     * 
     * @param id ASIN与型号关联ID
     * @return 结果
     */
    @Override
    public int deleteAsinTypeRelationById(Long id)
    {
        return asinTypeRelationMapper.deleteAsinTypeRelationById(id);
    }

    @Override
    public String importAsinTypeRelation(List<AsinTypeRelation> relationList, boolean updateSupport) {
        if (StringUtils.isNull(relationList) || relationList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (AsinTypeRelation relation : relationList)
        {
            try
            {
                // 验证是否存在这个数据
                AsinTypeRelation r = asinTypeRelationMapper.selectAsinTypeRelationByAsin(relation.getAsin());
                if (StringUtils.isNull(r)&&StringUtils.isNotEmpty(relation.getAsin())&&StringUtils.isNotEmpty(relation.getType()))
                {
                    this.insertAsinTypeRelation(relation);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、ASIN " + relation.getAsin() + " 导入成功");
                }
                else if (updateSupport&&StringUtils.isNotEmpty(relation.getAsin())&&StringUtils.isNotEmpty(relation.getType()))
                {
                    asinTypeRelationMapper.updateAsinTypeRelationByAsin(relation);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、ASIN " + relation.getAsin() + " 更新成功");
                }
                else
                {
                    if(StringUtils.isEmpty(relation.getAsin())||StringUtils.isEmpty(relation.getType())) continue;
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、ASIN " + relation.getAsin() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、ASIN " + relation.getAsin() + " 导入失败：";
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

    @Override
    //@Transactional
    public String syncAsinTypeRalation2AdvertisingData() {
        int successNum = 0;
        List<Advertising> advertisingList = advertisingService.selectAdvertisingWhenTypeIsNull();
        try{
             successNum = asinTypeRelationMapper.syncAsinTypeRalation2AdvertisingData();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(advertisingList.size()==0){
            return "没有需要更新的数据";
        }
        return successNum+"条数据更新成功！";
    }

    @Override
    public AsinTypeRelation selectAsinTypeRelationByAsin(String asin) {
        return asinTypeRelationMapper.selectAsinTypeRelationByAsin(asin);
    }
}
