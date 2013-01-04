package com.fozzy.apt.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import com.fozzy.apt.util.ProcessorLogger;

@SupportedAnnotationTypes({ "com.fozzy.api.annotation.Model", "com.fozzy.api.annotation.WebServiceHelper", "com.fozzy.api.annotation.WebServiceMethod" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class FozzyProcessor extends AbstractProcessor {

	private ProcessorLogger logger;

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		this.logger = new ProcessorLogger(processingEnv.getMessager());
		logger.info("Running FozzyProcessor...");

		// Exit early if no annotations in this round so we don't overwrite the
		// env file
		if (annotations.size() < 1) {
			return true;
		}

		for (TypeElement annotationType : annotations) {
			
			logger.info("FozzyProcessor Processing elements with @" + annotationType.getQualifiedName());
			logger.info("AnnotationType kind = " + annotationType.getKind());
			logger.info("AnnotationType modifiers = " + annotationType.getModifiers());
			logger.info("AnnotationType interfaces = " + annotationType.getInterfaces());
			logger.info("AnnotationType simpleName = " + annotationType.getSimpleName());
			logger.info("AnnotationType getTypeParameters = " + annotationType.getTypeParameters());

		}

		return true;
	}

}
