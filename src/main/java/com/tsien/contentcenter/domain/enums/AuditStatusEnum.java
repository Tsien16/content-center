package com.tsien.contentcenter.domain.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/8/7 0007 3:32
 */

public enum AuditStatusEnum {

    /**
     * 待审核
     */
    NOT_YET("NOT_YET"),

    /**
     * 审核通过
     */
    PASS("PASS"),

    /**
     * 审核不通过
     */
    REJECT("REJECT");

    /**
     * zi
     */
    private final String value;

    AuditStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
