package ${classModel.packageName};

<#list imports as import>
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
public class ${classModel.className} {

<#list methods as method>
	public static ${method.returnType.className} ${method.name}() {
	
		String url = "${method.url}";
		return null;
	}
	
</#list>  
}
