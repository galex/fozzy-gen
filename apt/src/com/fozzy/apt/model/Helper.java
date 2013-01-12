package com.fozzy.apt.model;

import java.util.ArrayList;

/**
 * Gather all the data to generate a helper class
 * 
 * @author Alexander Gherschon
 * 
 */
public class Helper {

	private ClassModel classModel;
	private ClassModel implementedClassModel;
	
	private ArrayList<String> imports;
	private ArrayList<Method> methods;

	public Helper() {
		super();
		imports = new ArrayList<String>();
		methods = new ArrayList<Method>();
	}

	public ArrayList<String> getImports() {
		return imports;
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

	public ClassModel getClassModel() {
		return classModel;
	}

	public void setClassModel(ClassModel helperClassModel) {
		this.classModel = helperClassModel;
	}

	public ClassModel getImplementedClassModel() {
		return implementedClassModel;
	}

	public void setImplementedClassModel(ClassModel implementedClassModel) {
		this.implementedClassModel = implementedClassModel;
	}

	@Override
	public String toString() {
		return "Helper [classModel=" + classModel + ", implementedClassModel=" + implementedClassModel + ", imports=" + imports + ", methods=" + methods + "]";
	}

	

}
