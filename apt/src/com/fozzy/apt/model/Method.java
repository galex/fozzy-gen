package com.fozzy.apt.model;

import java.util.ArrayList;

public class Method {

	private String name;
	private String url;
	private ClassModel returnType;
	private ArrayList<ClassModel> parameters;

	public Method() {
		super();
		parameters = new ArrayList<ClassModel>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ClassModel getReturnType() {
		return returnType;
	}

	public void setReturnType(ClassModel returnType) {
		this.returnType = returnType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<ClassModel> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<ClassModel> parameters) {
		this.parameters = parameters;
	}



}
