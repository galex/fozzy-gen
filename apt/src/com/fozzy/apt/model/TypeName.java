package com.fozzy.apt.model;

import java.util.Set;

public interface TypeName {

	Set<String> getImports();

	String getSimpleName();
	
	boolean isPrimitive();
}
