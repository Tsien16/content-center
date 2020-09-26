package com.tsien.contentcenter.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 *
 * @author tsien
 * @version 1.0.0
 * @date 2020/9/26 0026 13:15
 */

@Target({ElementType.METHOD})
@Documented
public @interface CheckLogin {
}
