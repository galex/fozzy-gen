package com.fozzy.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface WebServiceHelper {
	
	String name();
	String helperPackage();
	String parserPackage();
}
