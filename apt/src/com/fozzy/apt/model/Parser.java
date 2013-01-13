package com.fozzy.apt.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.fozzy.api.model.UrlFormatType;


public class Parser {

	private TypeName genericType;
	private ClassModelName classModelName;
	private UrlFormatType urlFormatType;
	
	public TypeName getGenericType() {
		return genericType;
	}
	public void setGenericType(TypeName genericType) {
		this.genericType = genericType;
	}
	public ClassModelName getClassModelName() {
		return classModelName;
	}
	public void setClassModelName(ClassModelName classModelName) {
		this.classModelName = classModelName;
	}
	
	
	public HashSet<String> getImports() {
		HashSet<String> imports = new HashSet<String>();
		imports.addAll(genericType.getImports());
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
	public UrlFormatType getUrlFormatType() {
		return urlFormatType;
	}
	public void setUrlFormatType(UrlFormatType urlFormatType) {
		this.urlFormatType = urlFormatType;
	}
}
