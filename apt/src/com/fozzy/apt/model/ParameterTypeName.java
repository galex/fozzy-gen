package com.fozzy.apt.model;

import java.util.Set;

public class ParameterTypeName implements Importable {

	private TypeName typeName;
	private String name;
	
	public ParameterTypeName(TypeName typeName, String name) {
		super();
		this.typeName = typeName;
		this.name = name;
	}
	public TypeName getTypeName() {
		return typeName;
	}
	public void setTypeName(TypeName typeName) {
		this.typeName = typeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "ParameterTypeName [typeName=" + typeName + ", name=" + name + "]";
	}
	@Override
	public Set<String> getImports() {
		
		return typeName.getImports();
	}
	
}
