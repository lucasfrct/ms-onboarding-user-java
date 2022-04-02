package com.environment.infrastructure.utils;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RemoveChar {
    // System.out.println("Run custom Annotation");
    public String key() default "";
}
