package com.ruoyi.project.pms.badcommodity.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.oms.oderReturn.domain.OrderReturn;
import com.ruoyi.project.oms.oderReturn.service.IOrderReturnService;
import com.ruoyi.project.oms.oderReturn.vo.OrderReturnVo;
import com.ruoyi.project.oms.orderRefund.domain.OrderRefund;
import com.ruoyi.project.oms.orderRefund.service.IOrderRefundService;
import com.ruoyi.project.oms.orderReturnRepeat.domain.OrderReturnRepeat;
import com.ruoyi.project.pms.badcommodity.domain.BadCommodityRepeat;
import com.ruoyi.project.pms.badcommodity.service.IBadCommodityRepeatService;
import com.ruoyi.project.pms.badcommodity.vo.BadCommodityExcelExpVO;
import com.ruoyi.project.pms.badcommodity.vo.BadCommodityPicture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.pms.badcommodity.mapper.BadCommodityMapper;
import com.ruoyi.project.pms.badcommodity.domain.BadCommodity;
import com.ruoyi.project.pms.badcommodity.service.IBadCommodityService;
import com.ruoyi.common.utils.text.Convert;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

/**
 * 不良记录Service业务层处理
 * 
 * @author Beignet
 * @date 2021-02-01
 */
@Service
public class BadCommodityServiceImpl implements IBadCommodityService 
{
    private static final Logger log = LoggerFactory.getLogger(BadCommodity.class);

    @Autowired
    private BadCommodityMapper badCommodityMapper;

    @Autowired
    private IOrderRefundService orderRefundService;

    @Autowired
    private IOrderReturnService orderReturnService;

    @Autowired
    private IBadCommodityRepeatService badCommodityRepeatService;

    /**
     * 查询不良记录
     * 
     * @param id 不良记录ID
     * @return 不良记录
     */
    @Override
    public BadCommodity selectBadCommodityById(Long id)
    {

        BadCommodity badCommodity = badCommodityMapper.selectBadCommodityById(id);
        if(StringUtils.isNotEmpty(badCommodity.getPicUrl1())){
            List<BadCommodityPicture> badCommodityPictureList =
                    (List<BadCommodityPicture>) JSONArray.parseArray(badCommodity.getPicUrl1(), BadCommodityPicture.class);
            badCommodity.setBadCommodityPicturebList(badCommodityPictureList);
        }
        return badCommodity;
    }

    /**
     * 查询不良记录列表
     * 
     * @param badCommodity 不良记录
     * @return 不良记录
     */
    @Override
    public List<BadCommodity> selectBadCommodityList(BadCommodity badCommodity)
    {
        List<BadCommodity> badCommodityList = badCommodityMapper.selectBadCommodityList(badCommodity);
        for(BadCommodity domain:badCommodityList){
            if(StringUtils.isNotEmpty(domain.getPicUrl1())){
                List<BadCommodityPicture> badCommodityPictureList =
                        (List<BadCommodityPicture>) JSONArray.parseArray(domain.getPicUrl1(), BadCommodityPicture.class);
                domain.setBadCommodityPicturebList(badCommodityPictureList);
            }
        }
        return badCommodityList;
    }

    /**
     * 新增不良记录
     * 
     * @param badCommodity 不良记录
     * @return 结果
     */
    @Override
    public int insertBadCommodity(BadCommodity badCommodity)
    {
        badCommodity.setCreateTime(DateUtils.getNowDate());
        return badCommodityMapper.insertBadCommodity(badCommodity);
    }

    /**
     * 修改不良记录
     * 
     * @param badCommodity 不良记录
     * @return 结果
     */
    @Override
    public int updateBadCommodity(BadCommodity badCommodity)
    {
        badCommodity.setUpdateTime(DateUtils.getNowDate());
        return badCommodityMapper.updateBadCommodity(badCommodity);
    }

    /**
     * 删除不良记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBadCommodityByIds(String ids)
    {
        return badCommodityMapper.deleteBadCommodityByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除不良记录信息
     * 
     * @param id 不良记录ID
     * @return 结果
     */
    @Override
    public int deleteBadCommodityById(Long id)
    {
        return badCommodityMapper.deleteBadCommodityById(id);
    }

    /**
     * 导入不良记录
     *
     * @param badCommodityList 不良记录List数据
     * @return 导入结果
     */
    @Override
    public String importBadCommodity(List<BadCommodity> badCommodityList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(badCommodityList) || badCommodityList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        int repeatNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        StringBuilder repeatMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();

        //保存重复数据
        //保留size>1的值，就是保留具有重复数据的value
        Map<String, List<BadCommodity>> repeatMap = badCommodityList.stream().collect(Collectors.groupingBy(BadCommodity::getOrderId));
        repeatMap.entrySet().removeIf(m -> m.getValue().size()==1);
        String repeatId = UUID.randomUUID().toString().replaceAll("-","");
        if(repeatMap!=null&&repeatMap.size()>0)
            for(List<BadCommodity> list : repeatMap.values()){
                for (BadCommodity badCommodity:list){
                    BadCommodityRepeat badCommodityRepeat = new BadCommodityRepeat();
                    BeanUtils.copyProperties(badCommodity,badCommodityRepeat);
                    badCommodityRepeat.setRepeatId(repeatId);
                    badCommodityRepeatService.insertBadCommodityRepeat(badCommodityRepeat);
                    repeatNum++;
                    repeatMsg.append("<br/>" + repeatNum + "、订单号为" + badCommodity.getOrderId()+" 的数据为此Excel的重复数据");
                }
            }

        for (BadCommodity badCommodity : badCommodityList)
        {
            try
            {
                // 验证数据是否已经
                BadCommodity domain = badCommodityMapper.selectBadCommodityByOnlyCondition(badCommodity);

                if(repeatMap.containsKey(badCommodity.getOrderId())) continue;//为重复数据时，则跳过

                if (domain==null)
                {
                    badCommodity.setCreateBy(operName);
                    badCommodity.setCreateTime(new Date());
                    this.insertBadCommodity(badCommodity);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ badCommodity.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    badCommodity.setUpdateBy(operName);
                    badCommodity.setUpdateTime(new Date());
                    badCommodityMapper.updateBadCommodityByOnlyCondition(badCommodity);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + badCommodity.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + badCommodity.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + badCommodity.toString()+" 的数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if(failureNum>0&&repeatNum>0){
            failureMsg.insert(0,"<button onclick=\"window.open('/pms/badCommodityRepeat?repeatId="+repeatId+"')\">查看重复数据</button><br/>共 "+repeatNum+" 条数据重复，共有 "+failureNum+" 条数据格式不正确。<br/>"+
                    "其中错误数据如下（Excel上的重复数据不列出）：");
            throw new BusinessException(failureMsg.toString());
        }
        else if(repeatNum>0){
            repeatMsg.insert(0,"<button onclick=\"window.open('/pms/badCommodityRepeat?repeatId="+repeatId+"')\">查看重复数据</button><br/>共 "+repeatNum+" 条数据重复,重复数据如下");
            throw new BusinessException(repeatMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Override
    public OrderReturnVo selectOrderReturnVo(String orderId,String sku) {
        OrderRefund orderRefundparam = new OrderRefund();
        orderRefundparam.setOrderId(orderId);
        orderRefundparam.setSku(sku);
        OrderRefund orderRefund = orderRefundService.selectOrderRefundByOnlyCondition(orderRefundparam);
        OrderReturn orderReturnParam = new OrderReturn();
        orderReturnParam.setOrderId(orderId);
        orderReturnParam.setSku(sku);
        OrderReturn orderReturn = orderReturnService.selectOrderReturnByOnlyCondition(orderReturnParam);
        OrderReturnVo orderReturnVo = new OrderReturnVo();
        if(orderRefund!=null) BeanUtils.copyProperties(orderRefund,orderReturnVo);
        if(orderReturn!=null) BeanUtils.copyProperties(orderReturn,orderReturnVo);
        return orderReturnVo;
    }

    @Override
    public int insertBadCommodityAndUploadPicture(BadCommodity badCommodity, List<MultipartFile> files) {
        BadCommodity eixtBadCommodity = badCommodityMapper.selectBadCommodityByOnlyCondition(badCommodity);
        if(eixtBadCommodity!=null) throw new BusinessException("新增数据已经存在！");
        int id =   badCommodityMapper.insertBadCommodity(badCommodity);
        List<BadCommodityPicture> pictureList = new ArrayList<>();
        int index = 1;
        for(MultipartFile file:files){
            try
            {
                if (!file.isEmpty())
                {
                    String filePathPrefix = RuoYiConfig.getProfile()+"/bad_commodity_pic";
                    String filePath = FileUploadUtils.upload(filePathPrefix, file);
                    BadCommodityPicture picture = new BadCommodityPicture();
                    picture.setPath(filePath);
                    picture.setFileName(file.getOriginalFilename());
                    picture.setExt(FileUtils.getExtByFileName(file.getOriginalFilename()));
                    picture.setPicNo("pic"+index);
                    pictureList.add(picture);
                    index++;
                }
            } catch (Exception e)
            {
                log.error("上传不良图片失败！", e);
                throw new BusinessException("上传不良图片失败！");
            }
        }
        if(pictureList.size()>0){
            badCommodity.setPicUrl1(JSON.toJSONString(pictureList));
            badCommodityMapper.updateBadCommodityByOnlyCondition(badCommodity);
        }
        return 1;
    }

    @Override
    public BadCommodityPicture uploadBadRecordPicture(MultipartFile file, BadCommodity badCommodity) {
        badCommodity = badCommodityMapper.selectBadCommodityByOnlyCondition(badCommodity);
        if(badCommodity==null) throw new BusinessException("该记录已被删除！无法上传图片！");

        List<BadCommodityPicture> badCommodityPictureList =StringUtils.isNotEmpty(badCommodity.getPicUrl1())?
                    (List<BadCommodityPicture>) JSONArray.parseArray(badCommodity.getPicUrl1(), BadCommodityPicture.class):new ArrayList<BadCommodityPicture>();

        try {
                if (!file.isEmpty()) {
                    String filePathPrefix = RuoYiConfig.getProfile()+"/bad_commodity_pic";
                    String filePath = FileUploadUtils.upload(filePathPrefix, file);
                    BadCommodityPicture picture = new BadCommodityPicture();
                    picture.setPath(filePath);
                    picture.setFileName(file.getOriginalFilename());
                    picture.setExt(FileUtils.getExtByFileName(file.getOriginalFilename()));
                    picture.setPicNo(getNewPicNoByPicList(badCommodityPictureList));
                    badCommodityPictureList.add(picture);
                    badCommodity.setPicUrl1(JSON.toJSONString(badCommodityPictureList));
                    badCommodityMapper.updateBadCommodity(badCommodity);
                    return picture;
                }else{
                    throw new BusinessException("上传不良图片失败！");
                }
            } catch (Exception e)
            {
                log.error("上传不良图片失败！", e);
                throw new BusinessException("上传不良图片失败！");
            }
    }

    private String getNewPicNoByPicList(List<BadCommodityPicture> badCommodityPictureList) {
        if(badCommodityPictureList==null||badCommodityPictureList.size()==0) return 0+"";
        BadCommodityPicture badCommodityPicture = badCommodityPictureList.get(badCommodityPictureList.size()-1);
        int picNo = Integer.parseInt(badCommodityPicture.getPicNo());
        return picNo+1+"";
    }

    @Override
    public int deleteBadRecordPicture(Long id, String picNo) {
        if(id==null||StringUtils.isEmpty(picNo)){
            throw new BusinessException("请求参数异常！");
        }

        BadCommodity badCommodity = badCommodityMapper.selectBadCommodityById(id);
        List<BadCommodityPicture> pictures = (List<BadCommodityPicture>)JSON.parseArray(badCommodity.getPicUrl1(),BadCommodityPicture.class);
        //正确 可删除多个
        Iterator<BadCommodityPicture> iterator = pictures.iterator();
        while (iterator.hasNext()) {
            BadCommodityPicture pic = iterator.next();
            if (StringUtils.equals(picNo,pic.getPicNo())) {
                iterator.remove();//使用迭代器的删除方法删除
            }
        }
        String picStr = JSON.toJSONString(pictures);
        badCommodity.setPicUrl1(picStr);
        return badCommodityMapper.updateBadCommodity(badCommodity);
    }

    @Override
    public List<BadCommodityExcelExpVO> selectBadCommodityExcelExpVoList(BadCommodity badCommodity) {
        List<BadCommodity> badCommodityList = badCommodityMapper.selectBadCommodityList(badCommodity);
        List<BadCommodityExcelExpVO> excelExpVOList = new ArrayList<>();
        for(BadCommodity badCommodityDo:badCommodityList){
            BadCommodityExcelExpVO excelExpVO = new BadCommodityExcelExpVO();
            BeanUtils.copyProperties(badCommodityDo,excelExpVO);
            List<BadCommodityPicture> pics = (List<BadCommodityPicture>)JSON.parseArray(badCommodityDo.getPicUrl1(),BadCommodityPicture.class);
            excelExpVOList.add(excelExpVO);
            if(pics==null) continue;
            for (int i=0;i<pics.size();i++){
                if(i==1) excelExpVO.setPicUrl1(pics.get(i).getPath());
                else if(i==2) excelExpVO.setPicUrl2(pics.get(i).getPath());
                else if(i==3) excelExpVO.setPicUrl3(pics.get(i).getPath());
            }

        }
        return excelExpVOList;
    }
}
