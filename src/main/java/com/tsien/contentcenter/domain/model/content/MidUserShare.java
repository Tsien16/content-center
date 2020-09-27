package com.tsien.contentcenter.domain.model.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/27 0027 15:35
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MidUserShare {
    private Integer id;

    /**
     * share.id
     */
    private Integer shareId;

    /**
     * user.id
     */
    private Integer userId;
}