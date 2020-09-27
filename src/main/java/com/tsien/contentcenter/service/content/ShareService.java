package com.tsien.contentcenter.service.content;

import com.github.pagehelper.PageInfo;
import com.tsien.contentcenter.domain.dto.content.ShareAuditDTO;
import com.tsien.contentcenter.domain.dto.content.ShareDTO;
import com.tsien.contentcenter.domain.model.content.Share;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 2:58
 */

public interface ShareService {

    /**
     * 根据用户ID查找分享
     *
     * @param id id
     * @return ShareDTO
     */
    ShareDTO findById(Integer id);

    /**
     * 内容审核
     *
     * @param id       id
     * @param auditDTO auditDTO
     * @return Share
     */
    Share auditById(Integer id, ShareAuditDTO auditDTO);

    /**
     * 审核
     *
     * @param id       id
     * @param auditDTO auditDTO
     */
    void auditByIdInDb(Integer id, ShareAuditDTO auditDTO);

    /**
     * auditByIdWithRocketMqLog
     *
     * @param id            id
     * @param auditDTO      auditDTO
     * @param transactionId transactionId
     */
    void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO auditDTO, String transactionId);

    /**
     * 查询分享文章
     *
     * @param title    title
     * @param pageNo   pageNo
     * @param pageSize pageSize
     * @param userId   userId
     * @return shareDTO
     */
    PageInfo<Share> q(String title, Integer pageNo, Integer pageSize, Integer userId);

    /**
     * exchangeById
     *
     * @param id      id
     * @param request request
     * @return share
     */
    Share exchangeById(Integer id, HttpServletRequest request);
}
