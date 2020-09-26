package com.tsien.contentcenter.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/26 0026 17:12
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthorization {
    String value();
}
