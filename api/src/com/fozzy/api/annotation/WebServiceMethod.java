package com.fozzy.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.fozzy.api.model.UrlFormatType;

@Target(ElementType.METHOD)
public @interface WebServiceMethod {
	
	String url();
	UrlFormatType urlFormatType();
}
