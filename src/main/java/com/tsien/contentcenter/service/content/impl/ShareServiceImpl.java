package com.tsien.contentcenter.service.content.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tsien.contentcenter.dao.content.MidUserShareMapper;
import com.tsien.contentcenter.dao.content.RocketmqTransactionLogMapper;
import com.tsien.contentcenter.dao.content.ShareMapper;
import com.tsien.contentcenter.domain.dto.content.ShareAuditDTO;
import com.tsien.contentcenter.domain.dto.content.ShareDTO;
import com.tsien.contentcenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.tsien.contentcenter.domain.dto.user.UserAddBonusDTO;
import com.tsien.contentcenter.domain.dto.user.UserDTO;
import com.tsien.contentcenter.domain.enums.AuditStatusEnum;
import com.tsien.contentcenter.domain.model.content.MidUserShare;
import com.tsien.contentcenter.domain.model.content.RocketmqTransactionLog;
import com.tsien.contentcenter.domain.model.content.Share;
import com.tsien.contentcenter.feignclient.UserCenterFeignClient;
import com.tsien.contentcenter.service.content.ShareService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/8/4 0004 17:09
 */

@Service
public class ShareServiceImpl implements ShareService {

    @Resource
    private ShareMapper shareMapper;

    @Resource
    private UserCenterFeignClient userCenterFeignClient;

    @Resource
    private MidUserShareMapper midUserShareMapper;

    @Resource
    private Source source;

    @Resource
    private RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    /**
     * 通过ID获取分享详细
     *
     * @param id id
     * @return Share
     */
    @Override
    public ShareDTO findById(Integer id) {

        // 获取发布详情
        Share share = shareMapper.selectByPrimaryKey(id);

        // 发布人ID
        Integer userId = share.getUserId();

        // 使用OpenFeign 获取用户中心UserDTO
        UserDTO userDTO = userCenterFeignClient.findById(userId);

        // 消息装配
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO != null ? userDTO.getWxNickname() : null);

        return shareDTO;
    }

    /**
     * 内容审核
     *
     * @param id       id
     * @param auditDTO auditDTO
     * @return Share
     */
    @Override
    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        // 1 查询share是否存在,或者当前的audit_status是否是not_yet
        Share share = shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法,该分享不存在");
        }

        if (StringUtils.equals(AuditStatusEnum.NOT_YET.getValue(), share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法,该分享已审核过");
        }

        // 2 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
            String transactionId = UUID.randomUUID().toString();

            source.output().send(
                    MessageBuilder.withPayload(UserAddBonusMsgDTO.builder()
                            .userId(share.getUserId())
                            .bonus(50)
                            .build())
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
                            .setHeader("share_id", id)
                            .setHeader("dto", JSON.toJSONString(auditDTO))
                            .build()
            );
        } else {
            auditByIdInDb(id, auditDTO);
        }

        return share;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void auditByIdInDb(Integer id, ShareAuditDTO auditDTO) {
        Share share = Share.builder()
                .id(id)
                .auditStatus(auditDTO.getAuditStatusEnum().getValue())
                .reason(auditDTO.getReason())
                .build();

        shareMapper.updateByPrimaryKeySelective(share);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO auditDTO, String transactionId) {

        auditByIdInDb(id, auditDTO);
        rocketmqTransactionLogMapper.insertSelective(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .log("审核分享")
                        .build()
        );

    }

    /**
     * 查询分享文章
     *
     * @param title    title
     * @param pageNo   pageNo
     * @param pageSize pageSize
     * @param userId   userId
     * @return shareDTO
     */
    @Override
    public PageInfo<Share> q(String title, Integer pageNo, Integer pageSize, Integer userId) {

        PageHelper.startPage(pageNo, pageSize);
        List<Share> shares = shareMapper.selectByParam(title);
        List<Share> sharesDeal = new ArrayList<>();

        // 1.如果用户未登录，那么downloadUrl全部设为null
        // 2.如果用户登录了，那么查询一下mid_user_share,如果没有数据,那么这条share的downloadUrl也为空
        if (userId == null) {
            sharesDeal = shares.stream().peek(
                    share -> {
                        share.setDownloadUrl(null);
                    }).collect(Collectors.toList());
        } else {
            sharesDeal = shares.stream().peek(
                    share -> {
                        MidUserShare midUserShare = MidUserShare.builder()
                                .userId(userId)
                                .shareId(share.getId())
                                .build();
                        midUserShareMapper.select(midUserShare);
                        if (midUserShare == null) {
                            share.setDownloadUrl(null);
                        }
                    }).collect(Collectors.toList());
        }


        return new PageInfo<>(sharesDeal);
    }

    /**
     * exchangeById
     *
     * @param id      id
     * @param request request
     * @return share
     */
    @Override
    public Share exchangeById(Integer id, HttpServletRequest request) {
        // 1.根据ID查询share，校验是否存在
        Integer usrId = (Integer) request.getAttribute("id");

        Share share = shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("该分享不存在！");
        }

        // 如果当前用户已经兑换过该分享，则直接放返回
        MidUserShare midUserShare = midUserShareMapper.select(
                MidUserShare.builder()
                        .userId(usrId)
                        .shareId(id)
                        .build()
        );

        if (midUserShare != null) {
            return share;
        }

        // 2.根据当前登录的用户id，查询积分是否够

        UserDTO userDTO = userCenterFeignClient.findById(usrId);

        if (share.getPrice() > userDTO.getBonus()) {
            throw new IllegalArgumentException("用户积分不够");
        }

        // 3.扣减积分 & 往mid_user_share里插入一条数据
        userCenterFeignClient.addBonus(
                UserAddBonusDTO.builder()
                        .userId(usrId)
                        .bonus(-share.getPrice())
                        .build()
        );

        midUserShareMapper.insertSelective(
                MidUserShare.builder()
                        .userId(usrId)
                        .shareId(id)
                        .build()
        );

        return share;
    }


}
