package com.fozzy.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface WebServiceMethod {
	
	String url() default "";
	int resourceId() default 0;
}
