package com.tsien.contentcenter.dao.content;

import com.tsien.contentcenter.domain.model.content.MidUserShare;


/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/27 0027 15:35
 */


public interface MidUserShareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MidUserShare record);

    int insertSelective(MidUserShare record);

    MidUserShare select(MidUserShare record);

    MidUserShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MidUserShare record);

    int updateByPrimaryKey(MidUserShare record);
}