package com.fozzy.apt.model;

public class ClassModel {

	private String packageName;
	private String className;
	
	public ClassModel(String qualifiedClassName) {

		int lastDot = qualifiedClassName.lastIndexOf('.');
		if (lastDot < 1) {
			throw new IllegalArgumentException("The default package is not allowed for type " + qualifiedClassName);
		}
		String pkg = qualifiedClassName.substring(0, lastDot);
		this.setPackageName(pkg);
		this.setClassName(qualifiedClassName.substring(lastDot + 1));
	}
	
	public ClassModel(String packageName, String className) {
		super();
		this.packageName = packageName;
		this.className = className;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "ClassModel [packageName=" + packageName + ", className=" + className + "]";
	}	
	
	public String getQualifiedClassName(){
		
		return packageName + "." + className;
	}
}
