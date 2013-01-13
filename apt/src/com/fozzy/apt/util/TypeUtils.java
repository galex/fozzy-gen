package com.fozzy.apt.util;

import java.util.ArrayList;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import com.fozzy.apt.model.ClassModelName;
import com.fozzy.apt.model.TypeName;
import com.fozzy.apt.model.TypeNameClass;
import com.fozzy.apt.model.TypeNamePrimitive;

public class TypeUtils {

	public static TypeName getTypeName(TypeMirror typeMirror) {

		TypeName typeName = null;

		if (typeMirror.getKind() == TypeKind.DECLARED) {

			DeclaredType declaredType = (DeclaredType) typeMirror;

			TypeNameClass typeNameClass = new TypeNameClass();
			typeNameClass.setType(new ClassModelName(extractTypeName(declaredType.toString())));

			if (declaredType.getTypeArguments().size() > 0) {

				ArrayList<TypeNameClass> parameters = new ArrayList<TypeNameClass>();

				for (TypeMirror typeArgument : declaredType.getTypeArguments()) {

					if (typeArgument.getKind() == TypeKind.DECLARED) {

						parameters.add((TypeNameClass) TypeUtils.getTypeName((DeclaredType) typeArgument));
					}
				}
				typeNameClass.setParameters(parameters);
			}
			typeName = typeNameClass;
		
		} else {
			
			if (TypeUtils.isPrimitive(typeMirror)){
				
				typeName = new TypeNamePrimitive(typeMirror.toString());
			}
			else {
				
				throw new UnsupportedOperationException("Unsupported type = " + typeMirror.getKind());
			}
		}
		return typeName;
	}

	public static boolean isPrimitive(TypeMirror typeMirror) {

		return typeMirror.getKind() == TypeKind.BOOLEAN || typeMirror.getKind() == TypeKind.BYTE || typeMirror.getKind() == TypeKind.CHAR || typeMirror.getKind() == TypeKind.DOUBLE || typeMirror.getKind() == TypeKind.FLOAT || typeMirror.getKind() == TypeKind.INT
				|| typeMirror.getKind() == TypeKind.LONG || typeMirror.getKind() == TypeKind.SHORT || typeMirror.getKind() == TypeKind.VOID;
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
