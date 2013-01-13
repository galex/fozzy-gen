package com.fozzy.apt.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fozzy.api.model.UrlFormatType;

public class Method implements Importable {

	private String name;
	private String url;
	private UrlFormatType urlFormatType;
	private TypeName returnType;
	private ArrayList<ParameterTypeName> parameters;
	
	private Parser parser;

	public Method() {
		super();
		parameters = new ArrayList<ParameterTypeName>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeName getReturnType() {
		return returnType;
	}

	public void setReturnType(TypeName returnType) {
		this.returnType = returnType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<ParameterTypeName> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<ParameterTypeName> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "Method [name=" + name + ", url=" + url + ", returnType=" + returnType + ", parameters=" + parameters + "]";
	}

	@Override
	public Set<String> getImports() {
		HashSet<String> methodImports = new HashSet<String>();
		
		Set<String> returnTypeImports = getReturnType().getImports();
		if(returnTypeImports != null) methodImports.addAll(returnTypeImports);
		
		if(parser != null){
			methodImports.add(parser.getClassModelName().getQualifiedClassName());
		}
		
		for(ParameterTypeName param : parameters){
			
			Set<String> paramTypeImports = param.getImports();
			if(paramTypeImports != null) methodImports.addAll(paramTypeImports);
		}
		
		return methodImports;
	}

	public UrlFormatType getUrlFormatType() {
		return urlFormatType;
	}

	public void setUrlFormatType(UrlFormatType urlFormatType) {
		this.urlFormatType = urlFormatType;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}
}
