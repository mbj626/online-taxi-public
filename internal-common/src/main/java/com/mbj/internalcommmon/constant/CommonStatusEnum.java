package com.mbj.internalcommmon.constant;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: mbj
 * @CreateTime: 2023-01-25 15:24
 * @Description:
 * @Version:
 */
public enum CommonStatusEnum {
    /**
     * 成功
     */
    SUCCESS(1,"success"),

    /**
     * 失败
     */
    FAIL(0,"fail")
    ;

    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
