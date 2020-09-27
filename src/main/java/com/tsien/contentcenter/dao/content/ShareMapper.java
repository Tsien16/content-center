package com.tsien.contentcenter.dao.content;

import com.tsien.contentcenter.domain.model.content.Share;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/7/19 0019 2:38
 */

public interface ShareMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(Share record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(Share record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    Share selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Share record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Share record);

    /**
     * 查询文章
     *
     * @param title title
     * @return shareList
     */
    List<Share> selectByParam(@Param("title") String title);
}