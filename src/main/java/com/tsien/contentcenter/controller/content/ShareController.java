package com.tsien.contentcenter.controller.content;

import com.github.pagehelper.PageInfo;
import com.tsien.contentcenter.auth.CheckLogin;
import com.tsien.contentcenter.domain.dto.content.ShareDTO;
import com.tsien.contentcenter.domain.model.content.Share;
import com.tsien.contentcenter.service.content.ShareService;
import com.tsien.contentcenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 2:59
 */

@RestController
@RequestMapping("/shares")
public class ShareController {

    @Resource
    private ShareService shareService;

    @Resource
    private JwtOperator jwtOperator;

    @GetMapping("/{id}")
    @CheckLogin
    public ShareDTO findById(@PathVariable Integer id) {

        return shareService.findById(id);
    }

    @GetMapping("/q")
    public PageInfo<Share> q(@RequestParam(required = false) String title,
                             @RequestParam(required = false, defaultValue = "1") int pageNo,
                             @RequestParam(required = false, defaultValue = "10") int pageSize,
                             @RequestHeader(value = "X-Token", required = false) String token) {
        if (pageSize > 100) {
            pageSize = 100;
        }

        Integer userId = null;
        if (StringUtils.isNoneBlank(token)) {
            Claims claims = jwtOperator.getClaimsFromToken(token);
            userId = (Integer) claims.get("id");
        }


        return shareService.q(title, pageNo, pageSize, userId);
    }

    @GetMapping("/exchange/{id}")
    @CheckLogin
    public Share exchangeById(@PathVariable Integer id, HttpServletRequest request) {
        return shareService.exchangeById(id, request);
    }
}
