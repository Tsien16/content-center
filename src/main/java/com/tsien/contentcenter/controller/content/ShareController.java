package com.tsien.contentcenter.controller.content;

import com.tsien.contentcenter.domain.dto.content.ShareDTO;
import com.tsien.contentcenter.service.content.ShareService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @GetMapping("/{id}")
    public ShareDTO findById(@PathVariable Integer id) {
        return shareService.findById(id);
    }
}
