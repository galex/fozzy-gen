package com.fozzy.apt.model;

import java.util.ArrayList;

public class Method {

	private String name;
	private String url;
	private TypeName returnType;
	private ArrayList<ClassModelName> parameters;

	public Method() {
		super();
		parameters = new ArrayList<ClassModelName>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeName getReturnType() {
		return returnType;
	}

	public void setReturnType(TypeName returnType) {
		this.returnType = returnType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<ClassModelName> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<ClassModelName> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "Method [name=" + name + ", url=" + url + ", returnType=" + returnType + ", parameters=" + parameters + "]";
	}
}
