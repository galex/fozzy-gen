package com.fozzy.apt.util;

import java.util.ArrayList;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import com.fozzy.apt.model.ClassModelName;
import com.fozzy.apt.model.TypeNameClass;

public class TypeUtils {

	public static TypeNameClass getTypeName(ProcessorLogger logger, DeclaredType declaredType) {

		logger.info("declaredType  = " + declaredType);

		TypeNameClass typeNameClass = new TypeNameClass();
		typeNameClass.setType(new ClassModelName(extractTypeName(declaredType.toString())));

		logger.info("declaredType parameters = " + declaredType.getTypeArguments());

		if (declaredType.getTypeArguments().size() > 0) {
			
			ArrayList<TypeNameClass> parameters = new ArrayList<TypeNameClass>();

			for (TypeMirror typeMirror : declaredType.getTypeArguments()) {

				if (typeMirror.getKind() == TypeKind.DECLARED) {

					parameters.add(TypeUtils.getTypeName(logger, (DeclaredType) typeMirror));
				}
			}
			typeNameClass.setParameters(parameters);
		}

		return typeNameClass;
	}
	
	public static boolean isPrimitive(TypeMirror typeMirror){
		
		return typeMirror.getKind() == TypeKind.BOOLEAN || 
				typeMirror.getKind() == TypeKind.BYTE ||
				typeMirror.getKind() == TypeKind.CHAR ||
				typeMirror.getKind() == TypeKind.DOUBLE ||
				typeMirror.getKind() == TypeKind.FLOAT ||
				typeMirror.getKind() == TypeKind.INT ||
				typeMirror.getKind() == TypeKind.LONG ||
				typeMirror.getKind() == TypeKind.SHORT; 
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
