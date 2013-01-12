package com.fozzy.apt.model;

import java.util.Set;

public class TypeNamePrimitive implements TypeName {

	private String primitive;

	public TypeNamePrimitive(String primitive) {
		super();
		this.primitive = primitive;
	}

	public String getPrimitive() {
		return primitive;
	}

	public void setPrimitive(String primitive) {
		this.primitive = primitive;
	}
	
	@Override
	public String getSimpleName() {
		
		return primitive.toLowerCase();
	}

	@Override
	public Set<String> getImports() {
		return null;
	}

	@Override
	public String toString() {
		return "TypeNamePrimitive [primitive=" + primitive + "]";
	}

	@Override
	public boolean isPrimitive() {
		return true;
	}
}
