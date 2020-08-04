package com.tsien.contentcenter.service.content;

import com.tsien.contentcenter.domain.dto.content.ShareDTO;

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
}
