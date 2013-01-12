package ${classModelName.packageName};

<#list listImports as import>
import ${import};
</#list>

/**
 * GENERATED CODE
 *
 * Provides a helper to access your web services
 * This class does NOT implement the defined Interface for the methods to be <b>static</b>.
 *
 * @author Alexander Gherschon
 */
public class ${classModelName.className} {

<#list methods as method>
	public static ${method.returnType.simpleName} ${method.name}() {
	
		String url = "${method.url}";

<#if "${method.returnType.simpleName}" == "int">
		return 0;
<#else>
		return null;
</#if>
	}
	
</#list>  
}
