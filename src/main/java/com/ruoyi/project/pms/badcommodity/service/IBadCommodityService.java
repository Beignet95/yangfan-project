package com.ruoyi.project.pms.badcommodity.service;

import java.util.List;

import com.ruoyi.project.oms.oderReturn.vo.OrderReturnVo;
import com.ruoyi.project.pms.badcommodity.domain.BadCommodity;
import com.ruoyi.project.pms.badcommodity.vo.BadCommodityExcelExpVO;
import com.ruoyi.project.pms.badcommodity.vo.BadCommodityPicture;
import org.springframework.web.multipart.MultipartFile;

/**
 * 不良记录Service接口
 * 
 * @author Beignet
 * @date 2021-02-01
 */
public interface IBadCommodityService 
{
    /**
     * 查询不良记录
     * 
     * @param id 不良记录ID
     * @return 不良记录
     */
    public BadCommodity selectBadCommodityById(Long id);

    /**
     * 查询不良记录列表
     * 
     * @param badCommodity 不良记录
     * @return 不良记录集合
     */
    public List<BadCommodity> selectBadCommodityList(BadCommodity badCommodity);

    /**
     * 新增不良记录
     * 
     * @param badCommodity 不良记录
     * @return 结果
     */
    public int insertBadCommodity(BadCommodity badCommodity);

    /**
     * 修改不良记录
     * 
     * @param badCommodity 不良记录
     * @return 结果
     */
    public int updateBadCommodity(BadCommodity badCommodity);

    /**
     * 批量删除不良记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBadCommodityByIds(String ids);

    /**
     * 删除不良记录信息
     * 
     * @param id 不良记录ID
     * @return 结果
     */
    public int deleteBadCommodityById(Long id);

    /**
     * 导入不良记录
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importBadCommodity(List<BadCommodity> badCommodityList, boolean isUpdateSupport);


    OrderReturnVo selectOrderReturnVo(String orderId,String sku);

    int insertBadCommodityAndUploadPicture(BadCommodity badCommodity, List<MultipartFile> files);

    BadCommodityPicture uploadBadRecordPicture(MultipartFile file, BadCommodity badCommodity);

    int deleteBadRecordPicture(Long id, String picNo);

    List<BadCommodityExcelExpVO> selectBadCommodityExcelExpVoList(BadCommodity badCommodity);
}
