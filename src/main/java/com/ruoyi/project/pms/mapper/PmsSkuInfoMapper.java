package com.ruoyi.project.pms.mapper;

import java.util.List;
import com.ruoyi.project.pms.domain.PmsSkuInfo;

/**
 * sku信息Mapper接口
 * 
 * @author ruoyi
 * @date 2021-01-07
 */
public interface PmsSkuInfoMapper 
{
    /**
     * 查询sku信息
     * 
     * @param skuId sku信息ID
     * @return sku信息
     */
    public PmsSkuInfo selectPmsSkuInfoById(Long skuId);

    /**
     * 查询sku信息列表
     * 
     * @param pmsSkuInfo sku信息
     * @return sku信息集合
     */
    public List<PmsSkuInfo> selectPmsSkuInfoList(PmsSkuInfo pmsSkuInfo);

    /**
     * 新增sku信息
     * 
     * @param pmsSkuInfo sku信息
     * @return 结果
     */
    public int insertPmsSkuInfo(PmsSkuInfo pmsSkuInfo);

    /**
     * 修改sku信息
     * 
     * @param pmsSkuInfo sku信息
     * @return 结果
     */
    public int updatePmsSkuInfo(PmsSkuInfo pmsSkuInfo);

    /**
     * 删除sku信息
     * 
     * @param skuId sku信息ID
     * @return 结果
     */
    public int deletePmsSkuInfoById(Long skuId);

    /**
     * 批量删除sku信息
     * 
     * @param skuIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSkuInfoByIds(String[] skuIds);
}
