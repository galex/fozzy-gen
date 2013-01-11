package com.fozzy.apt.model;

import java.util.HashMap;

public class Method {

	private String name;
	private String returnType;
	private HashMap<String, String> parametersTypesAndNames;

	public Method() {
		
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public HashMap<String, String> getParametersTypesAndNames() {
		return parametersTypesAndNames;
	}

	public void setParametersTypesAndNames(HashMap<String, String> parametersTypesAndNames) {
		this.parametersTypesAndNames = parametersTypesAndNames;
	}



}
