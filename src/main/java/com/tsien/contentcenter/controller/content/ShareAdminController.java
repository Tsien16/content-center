package com.tsien.contentcenter.controller.content;

import com.tsien.contentcenter.auth.CheckAuthorization;
import com.tsien.contentcenter.domain.dto.content.ShareAuditDTO;
import com.tsien.contentcenter.domain.model.content.Share;
import com.tsien.contentcenter.service.content.ShareService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/8/7 0007 3:29
 */

@RestController
@RequestMapping("/admin/shares")
public class ShareAdminController {

    @Resource
    private ShareService shareService;

    @PutMapping("/audit/{id}")
    @CheckAuthorization("admin")
    public Share auditById(@PathVariable Integer id, @RequestBody ShareAuditDTO auditDTO) {
        return shareService.auditById(id, auditDTO);
    }
}
