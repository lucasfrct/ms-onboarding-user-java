package com.environment.infrastructure.utils;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RemoveChar {
    // System.out.println("Run custom Annotation");
    // String str = "This#string%contains^special*characters&.";
    // str = str.replaceAll("[^a-zA-Z0-9]", " ");
    // System.out.println(str);
    // String phone;
    // phone.contains("[^a-zA-Z]");
    // public String key() default "";
}
