package com.fozzy.apt.model;

public class ClassModelName {

	private static final String SEPARATOR = ".";
	private String packageName;
	private String className;
	
	public ClassModelName(String qualifiedClassName) {

		int lastDot = qualifiedClassName.lastIndexOf('.');
		if (lastDot < 1) {
			
			this.className = qualifiedClassName;
		} else {
			String pkg = qualifiedClassName.substring(0, lastDot);
			this.setPackageName(pkg);
			this.setClassName(qualifiedClassName.substring(lastDot + 1));
		}
	}
	
	public ClassModelName(String packageName, String className) {
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
		
		return packageName + SEPARATOR + className;
	}
	
	@Override
	public boolean equals(Object other) {
		
		return (other instanceof ClassModelName && ((ClassModelName) other).getQualifiedClassName() != null && ((ClassModelName) other).getQualifiedClassName().equals(getQualifiedClassName()));
	}
	
	public boolean isPrimitive(){
		
		return packageName == null;
	}
}
