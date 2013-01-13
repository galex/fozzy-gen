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
public class Helper implements Importable {

	private String helperPackageName;
	private String parserPackageName;
	
	private ClassModelName classModelName;
	private ClassModelName implementedClassModel;
	
	private ArrayList<Method> methods;

	public Helper() {
		super();
		methods = new ArrayList<Method>();
	}

	
	public HashSet<String> getImports() {
		HashSet<String> imports = new HashSet<String>();
		imports.add(implementedClassModel.getQualifiedClassName());
		for(Method method : methods){
			imports.addAll(method.getImports());
		}
		return imports;
	}	
	
	//TODO freemarker helper, shoudn't be in the model
	public ArrayList<String> getListImports() {
		
		ArrayList<String> listImports = new ArrayList<String>();
		Iterator<String> it = getImports().iterator();
		while(it.hasNext()){
			listImports.add(it.next());
		}
		return listImports;
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
		return "Helper [classModel=" + classModelName + ", implementedClassModel=" + implementedClassModel + ", imports=" + getImports() + ", methods=" + methods + "]";
	}


	public String getHelperPackageName() {
		return helperPackageName;
	}


	public void setHelperPackageName(String helperPackageName) {
		this.helperPackageName = helperPackageName;
	}


	public String getParserPackageName() {
		return parserPackageName;
	}


	public void setParserPackageName(String parserPackageName) {
		this.parserPackageName = parserPackageName;
	}


	public ClassModelName getImplementedClassModel() {
		return implementedClassModel;
	}


	public void setImplementedClassModel(ClassModelName implementedClassModel) {
		this.implementedClassModel = implementedClassModel;
	}

	

}
