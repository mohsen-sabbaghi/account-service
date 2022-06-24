package com.example.accountservice.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author m-sabbaghi
 * <a href="https://www.linkedin.com/in/sabbaghi/">...</a>
 * @version 6/14/2022
 */
@Target({ElementType.METHOD})
@Retention(RUNTIME)
public @interface PreventDuplication {
    String parameterName() default "Track-Id";
}
