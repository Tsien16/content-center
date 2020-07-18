package com.tsien.contentcenter.service.content;

import com.tsien.contentcenter.dao.content.ShareMapper;
import com.tsien.contentcenter.domain.dto.content.ShareDTO;
import com.tsien.contentcenter.domain.dto.user.UserDTO;
import com.tsien.contentcenter.domain.model.content.Share;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 2:58
 */

@Service
public class ShareService {

    @Resource
    private ShareMapper shareMapper;

    /**
     * 通过ID获取分享详细
     *
     * @param id id
     * @return Share
     */
    public ShareDTO findById(Integer id) {

        // 获取发布详情
        Share share = shareMapper.selectByPrimaryKey(id);

        // 发布人ID
        Integer userId = share.getUserId();

        RestTemplate restTemplate = new RestTemplate();

        UserDTO userDTO = restTemplate.getForObject(
                "http://localhost:8080/users/{id}", UserDTO.class, userId
        );
        ShareDTO shareDTO = new ShareDTO();
        // 消息装配
        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO != null ? userDTO.getWxNickname() : null);

        return shareDTO;

    }
}
