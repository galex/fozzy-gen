package com.fozzy.apt.model;

import java.util.ArrayList;

/**
 * Gather all the data to generate a helper class
 * @author galex
 *
 */
public class Helper {

	private String name;
	private String extendingClass;
	private ArrayList<String> packages;
	private ArrayList<Method> methods;
	
	public Helper() {
		super();
	}
	
	public Helper(String name, String extendingClass, ArrayList<String> packages, ArrayList<Method> methods) {
		super();
		this.name = name;
		this.extendingClass = extendingClass;
		this.packages = packages;
		this.methods = methods;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExtendingClass() {
		return extendingClass;
	}
	public void setExtendingClass(String extendingClass) {
		this.extendingClass = extendingClass;
	}
	public ArrayList<String> getPackages() {
		return packages;
	}
	public void setPackages(ArrayList<String> packages) {
		this.packages = packages;
	}
	public ArrayList<Method> getMethods() {
		return methods;
	}
	public void setMethods(ArrayList<Method> methods) {
		this.methods = methods;
	}
}
