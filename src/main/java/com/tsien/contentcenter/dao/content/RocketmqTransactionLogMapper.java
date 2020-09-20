package com.tsien.contentcenter.dao.content;

import com.tsien.contentcenter.domain.model.content.RocketmqTransactionLog;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/20 0020 2:27
 */


public interface RocketmqTransactionLogMapper {
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
    int insert(RocketmqTransactionLog record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(RocketmqTransactionLog record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    RocketmqTransactionLog selectByPrimaryKey(Integer id);


    /**
     * rocketmqTransactionLog
     *
     * @param rocketmqTransactionLog rocketmqTransactionLog
     * @return rocketmqTransactionLog
     */
    RocketmqTransactionLog selectOne(RocketmqTransactionLog rocketmqTransactionLog);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(RocketmqTransactionLog record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(RocketmqTransactionLog record);
}