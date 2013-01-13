package ${classModelName.packageName};

<#list listImports as import>
import ${import};
</#list>
import org.json.*;
import com.fozzy.api.parser.AbstractJsonParser;

/**
 * GENERATED CODE
 *
 * Provides a json parser returning a ${genericType.simpleName} objects
 *
 * @author Alexander Gherschon
 */
public class ${classModelName.className} extends AbstractJsonParser<${genericType.simpleName}, JSONObject> {

}
