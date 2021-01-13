package com.ruoyi.project.pms.service.impl;

import java.util.List;

import com.ruoyi.project.pms.service.IPmsSkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.pms.mapper.PmsSkuInfoMapper;
import com.ruoyi.project.pms.domain.PmsSkuInfo;
import com.ruoyi.common.utils.text.Convert;

/**
 * sku信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-01-07
 */
@Service
public class PmsSkuInfoServiceImpl implements IPmsSkuInfoService
{
    @Autowired
    private PmsSkuInfoMapper pmsSkuInfoMapper;

    /**
     * 查询sku信息
     * 
     * @param skuId sku信息ID
     * @return sku信息
     */
    @Override
    public PmsSkuInfo selectPmsSkuInfoById(Long skuId)
    {
        return pmsSkuInfoMapper.selectPmsSkuInfoById(skuId);
    }

    /**
     * 查询sku信息列表
     * 
     * @param pmsSkuInfo sku信息
     * @return sku信息
     */
    @Override
    public List<PmsSkuInfo> selectPmsSkuInfoList(PmsSkuInfo pmsSkuInfo)
    {
        return pmsSkuInfoMapper.selectPmsSkuInfoList(pmsSkuInfo);
    }

    /**
     * 新增sku信息
     * 
     * @param pmsSkuInfo sku信息
     * @return 结果
     */
    @Override
    public int insertPmsSkuInfo(PmsSkuInfo pmsSkuInfo)
    {
        return pmsSkuInfoMapper.insertPmsSkuInfo(pmsSkuInfo);
    }

    /**
     * 修改sku信息
     * 
     * @param pmsSkuInfo sku信息
     * @return 结果
     */
    @Override
    public int updatePmsSkuInfo(PmsSkuInfo pmsSkuInfo)
    {
        return pmsSkuInfoMapper.updatePmsSkuInfo(pmsSkuInfo);
    }

    /**
     * 删除sku信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuInfoByIds(String ids)
    {
        return pmsSkuInfoMapper.deletePmsSkuInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除sku信息信息
     * 
     * @param skuId sku信息ID
     * @return 结果
     */
    @Override
    public int deletePmsSkuInfoById(Long skuId)
    {
        return pmsSkuInfoMapper.deletePmsSkuInfoById(skuId);
    }
}
