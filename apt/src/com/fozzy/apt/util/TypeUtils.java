package com.fozzy.apt.util;

import java.util.ArrayList;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import com.fozzy.apt.model.ClassModelName;
import com.fozzy.apt.model.TypeName;

public class TypeUtils {

	public static TypeName getTypeName(ProcessorLogger logger, DeclaredType declaredType) {

		logger.info("declaredType  = " + declaredType);

		TypeName typeName = new TypeName();
		typeName.setType(new ClassModelName(extractTypeName(declaredType.toString())));

		logger.info("declaredType parameters = " + declaredType.getTypeArguments());

		if (declaredType.getTypeArguments().size() > 0) {
			
			ArrayList<TypeName> parameters = new ArrayList<TypeName>();

			for (TypeMirror typeMirror : declaredType.getTypeArguments()) {

				if (typeMirror.getKind() == TypeKind.DECLARED) {

					parameters.add(TypeUtils.getTypeName(logger, (DeclaredType) typeMirror));
				}
			}
			typeName.setParameters(parameters);
		}

		return typeName;
	}

	private static String extractTypeName(String completeType) {

		String typeName = "";
		if (completeType.contains("<"))
			typeName = completeType.substring(0, completeType.indexOf("<"));
		else
			typeName = completeType;
		return typeName;
	}
}
