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
	
	/**
	 * Capitalizes the first letter to create a valid getter/setter name.
	 *
	 * @param String
	 * @return String
	 */
	public static String capFirst(String anyName) {
		// obscure Java convention:
		// if second letter capitalized, leave it alone
		if (anyName.length() > 1)
			if (anyName.charAt(1) >= 'A' && anyName.charAt(1) <= 'Z')
				return anyName;
		String capFirstLetter = anyName.substring(0, 1).toUpperCase();
		return capFirstLetter + anyName.substring(1);
	}
	
	public static String getParserName(String methodName){
		
		String parserName = null;
		if(methodName.startsWith("get")){
			parserName = methodName.substring(3, methodName.length());
		}
		
		return capFirst(parserName) + "Parser";
	}
}
