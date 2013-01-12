package com.fozzy.apt.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypeName {

	private List<TypeName> parameters;
	private ClassModelName type;

	/*
	 * public TypeName(String completeTypName, List<TypeName> params){
	 * 
	 * type = new ClassModelName(completeTypName); parameters = params; }
	 */

	public TypeName() {
	}

	public List<TypeName> getParameters() {
		return parameters;
	}

	public void setParameters(List<TypeName> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "TypeName [parameters=" + parameters + ", type=" + type + "]";
	}

	public Set<String> getImports() {

		HashSet<String> imports = new HashSet<String>();
		imports.add(type.getQualifiedClassName());
		if (parameters != null) {
			for (TypeName parameter : parameters) {
				imports.addAll(parameter.getImports());
			}
		}
		return imports;
	}

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
}