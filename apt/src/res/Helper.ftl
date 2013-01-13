package ${classModelName.packageName};

<#list listImports as import>
import ${import};
</#list>
import java.io.IOException;
import com.fozzy.api.util.HttpUtils;

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
	public static ${method.returnType.simpleName} ${method.name}(<@compress single_line=true>
	
	<#list method.parameters as parameter>
		${parameter.typeName.simpleName} ${parameter.name}
		<#if (parameter_index+1) < method.parameters?size>,</#if>
	</#list> 
	
	</@compress>) {

		${method.parser.genericType.classSimpleName} data = null;

		${method.parser.classModelName.className} parser = new ${method.parser.classModelName.className}();
		try {
			data = parser.parse(HttpUtils.get("${method.url}"));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
</#list>  
}
