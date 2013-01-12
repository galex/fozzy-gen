package com.fozzy.apt.util;

public class StringUtils {

	private static final String REGEX_SEPARATOR = "\\.";
	private static final String SEPARATOR = ".";

	public static String getPackageName(String completeClassName){
		
		String [] split = completeClassName.split(REGEX_SEPARATOR);
		
		String packageName = null;
		for(int i = 0; i < split.length - 1; i++){
			
			System.out.println(split[i]);
			
			if(packageName == null)
				packageName = split[i];
			else 
				packageName += SEPARATOR + split[i];
		}
		return packageName;
	}
	
	public static String getCompleteClassName(String packageName, String className){
		
		return packageName + SEPARATOR + className;
	}
}
