package com.fozzy.apt.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Gather all the data to generate a helper class
 * 
 * @author Alexander Gherschon
 * 
 */
public class Helper {

	private ClassModelName classModelName;
	private ClassModelName implementedClassModel;
	
	private HashSet<String> imports;
	private ArrayList<Method> methods;

	public Helper() {
		super();
		imports = new HashSet<String>();
		methods = new ArrayList<Method>();
	}

	
	public HashSet<String> getImports() {
		return imports;
	}	
	
	public ArrayList<String> getListImports() {
		
		ArrayList<String> listImports = new ArrayList<String>();
		Iterator<String> it = imports.iterator();
		while(it.hasNext()){
			listImports.add(it.next());
		}
		return listImports;
	}

	public void setImports(HashSet<String> imports) {
		this.imports = imports;
	}

	public ArrayList<Method> getMethods() {
		return methods;
	}

	public void setMethods(ArrayList<Method> methods) {
		this.methods = methods;
	}

	public ClassModelName getClassModelName() {
		return classModelName;
	}

	public void setClassModelName(ClassModelName helperClassModel) {
		this.classModelName = helperClassModel;
	}

	public ClassModelName getImplementedClassModelName() {
		return implementedClassModel;
	}

	public void setImplementedClassModelName(ClassModelName implementedClassModel) {
		this.implementedClassModel = implementedClassModel;
	}

	@Override
	public String toString() {
		return "Helper [classModel=" + classModelName + ", implementedClassModel=" + implementedClassModel + ", imports=" + imports + ", methods=" + methods + "]";
	}

	

}
