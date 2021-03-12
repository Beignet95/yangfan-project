package com.ruoyi.project.pms.asinPasin.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.pms.asinPasin.mapper.AsinPasinMapper;
import com.ruoyi.project.pms.asinPasin.domain.AsinPasin;
import com.ruoyi.project.pms.asinPasin.service.IAsinPasinService;
import com.ruoyi.common.utils.text.Convert;

/**
 * ASIN与父ASIN关系Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-08
 */
@Service
public class AsinPasinServiceImpl implements IAsinPasinService 
{
    private static final Logger log = LoggerFactory.getLogger(AsinPasin.class);

    @Autowired
    private AsinPasinMapper asinPasinMapper;

    /**
     * 查询ASIN与父ASIN关系
     * 
     * @param id ASIN与父ASIN关系ID
     * @return ASIN与父ASIN关系
     */
    @Override
    public AsinPasin selectAsinPasinById(Long id)
    {
        return asinPasinMapper.selectAsinPasinById(id);
    }

    /**
     * 查询ASIN与父ASIN关系列表
     * 
     * @param asinPasin ASIN与父ASIN关系
     * @return ASIN与父ASIN关系
     */
    @Override
    public List<AsinPasin> selectAsinPasinList(AsinPasin asinPasin)
    {
        return asinPasinMapper.selectAsinPasinList(asinPasin);
    }

    /**
     * 新增ASIN与父ASIN关系
     * 
     * @param asinPasin ASIN与父ASIN关系
     * @return 结果
     */
    @Override
    public int insertAsinPasin(AsinPasin asinPasin)
    {
        asinPasin.setCreateTime(DateUtils.getNowDate());
        return asinPasinMapper.insertAsinPasin(asinPasin);
    }

    /**
     * 修改ASIN与父ASIN关系
     * 
     * @param asinPasin ASIN与父ASIN关系
     * @return 结果
     */
    @Override
    public int updateAsinPasin(AsinPasin asinPasin)
    {
        asinPasin.setUpdateTime(DateUtils.getNowDate());
        return asinPasinMapper.updateAsinPasin(asinPasin);
    }

    /**
     * 删除ASIN与父ASIN关系对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAsinPasinByIds(String ids)
    {
        return asinPasinMapper.deleteAsinPasinByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除ASIN与父ASIN关系信息
     * 
     * @param id ASIN与父ASIN关系ID
     * @return 结果
     */
    @Override
    public int deleteAsinPasinById(Long id)
    {
        return asinPasinMapper.deleteAsinPasinById(id);
    }

    /**
     * 导入ASIN与父ASIN关系
     *
     * @param asinPasinList ASIN与父ASIN关系List数据
     * @return 导入结果
     */
    @Override
    public String importAsinPasin(List<AsinPasin> asinPasinList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(asinPasinList) || asinPasinList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (AsinPasin asinPasin : asinPasinList)
        {
            try
            {
                // 验证数据是否已经
                AsinPasin domain = asinPasinMapper.selectAsinPasinByOnlyCondition(asinPasin);
                if (domain==null)
                {
                    asinPasin.setCreateBy(operName);
                    asinPasin.setCreateTime(new Date());
                    this.insertAsinPasin(asinPasin);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ asinPasin.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    asinPasin.setUpdateBy(operName);
                    asinPasin.setUpdateTime(new Date());
                    asinPasinMapper.updateAsinPasinByOnlyCondition(asinPasin);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + asinPasin.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + asinPasin.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + asinPasin.toString()+" 的数据导入失败：";
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
}
