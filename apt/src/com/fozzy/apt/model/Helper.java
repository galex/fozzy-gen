package com.fozzy.apt.model;

import java.util.ArrayList;

/**
 * Gather all the data to generate a helper class
 * 
 * @author Alexander Gherschon
 * 
 */
public class Helper {

	private String classPackage;

	private String name;
	private String implementingInterface;
	private ArrayList<String> imports;

	private ArrayList<Method> methods;

	public Helper() {
		super();
		imports = new ArrayList<String>();
	}

	public ArrayList<String> getImports() {
		return imports;
	}

	public String getPackage() {
		return classPackage;
	}

	public void setPackage(String classPackage) {
		this.classPackage = classPackage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImports(ArrayList<String> imports) {
		this.imports = imports;
	}

	public ArrayList<Method> getMethods() {
		return methods;
	}

	public void setMethods(ArrayList<Method> methods) {
		this.methods = methods;
	}

	public String getImplementingInterface() {
		return implementingInterface;
	}

	public void setImplementingInterface(String implementingInterface) {
		this.implementingInterface = implementingInterface;
	}

	@Override
	public String toString() {
		return "Helper [name=" + name + ", implementingInterface=" + implementingInterface + ", packages=" + imports + ", methods=" + methods + "]";
	}

}
