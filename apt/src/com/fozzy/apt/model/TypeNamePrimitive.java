package com.fozzy.apt.model;

import java.util.HashSet;
import java.util.Set;

import com.fozzy.apt.util.TypeUtils;

public class TypeNamePrimitive implements TypeName, Importable {

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
		
		if(isPrimitive()){
			
			Set<String> imports = new HashSet<String>();
			
			System.out.println("packagename = " +TypeUtils.getClassModelName(this).getPackageName());
			System.out.println("classname = " + TypeUtils.getClassModelName(this).getClassName());
			
			imports.add(TypeUtils.getClassModelName(this).getQualifiedClassName());
			return imports;
		}
			
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
	
	@Override
	public String getClassSimpleName() {
		
		return TypeUtils.getClassModelName(this).getClassName();
	}
}
