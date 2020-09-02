package com.tsien.contentcenter.domain.dto.content;

import com.tsien.contentcenter.domain.enums.AuditStatusEnum;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/8/7 0007 3:31
 */

@Data
public class ShareAuditDTO {

    /**
     * 审核状态
     */
    private AuditStatusEnum auditStatusEnum;

    /**
     * 原因
     */
    private String reason;
}
