package com.fozzy.apt.model;

import java.util.Set;


public interface TypeName {

	String getSimpleName();
	boolean isPrimitive();
	Set<String> getImports(); //TODO why having Importable ? An interface can't implements another one? Too bad...
}
