package com.fozzy.apt.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypeNameClass implements TypeName {

	private List<TypeNameClass> parameters;
	private ClassModelName type;

	/*
	 * public TypeName(String completeTypName, List<TypeName> params){
	 * 
	 * type = new ClassModelName(completeTypName); parameters = params; }
	 */

	public TypeNameClass() {
	}

	public List<TypeNameClass> getParameters() {
		return parameters;
	}

	public void setParameters(List<TypeNameClass> parameters) {
		this.parameters = parameters;
	}

	public Set<String> getImports() {

		HashSet<String> imports = new HashSet<String>();
		imports.add(type.getQualifiedClassName());
		if (parameters != null) {
			for (TypeNameClass parameter : parameters) {
				imports.addAll(parameter.getImports());
			}
		}
		return imports;
	}
	
	@Override
	public String getSimpleName() {

		String simpleName = type.getClassName();
		if (parameters != null) {
			simpleName += "<";
			for (int i = 0; i < parameters.size(); i++) {
				if (i > 0 && i != parameters.size()) simpleName += ", ";
				simpleName += parameters.get(i).getSimpleName();	
			}
			simpleName += ">";
		}
		return simpleName;
	}

	public ClassModelName getType() {
		return type;
	}

	public void setType(ClassModelName className) {
		this.type = className;
	}

	@Override
	public String toString() {
		return "TypeNameClass [parameters=" + parameters + ", type=" + type + "]";
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}
}