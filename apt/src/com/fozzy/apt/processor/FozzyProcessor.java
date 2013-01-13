package com.fozzy.apt.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import com.fozzy.api.annotation.Model;
import com.fozzy.api.annotation.WebServiceHelper;
import com.fozzy.api.annotation.WebServiceMethod;
import com.fozzy.apt.model.ClassModelName;
import com.fozzy.apt.model.Helper;
import com.fozzy.apt.model.Method;
import com.fozzy.apt.model.ParameterTypeName;
import com.fozzy.apt.model.Parser;
import com.fozzy.apt.util.ProcessorLogger;
import com.fozzy.apt.util.TemplateGenerator;
import com.fozzy.apt.util.TypeUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;

@SupportedAnnotationTypes({ "com.fozzy.api.annotation.Model", "com.fozzy.api.annotation.WebServiceHelper", "com.fozzy.api.annotation.WebServiceMethod" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class FozzyProcessor extends AbstractProcessor {

	private Configuration cfg = new Configuration();
	private ProcessorLogger logger;

	private List<Helper> helpers = new ArrayList<Helper>();
	private HashMap<String, List<Method>> models = new HashMap<String, List<Method>>();

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		this.cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/res"));
		this.logger = new ProcessorLogger(processingEnv.getMessager());
		logger.info("Running FozzyProcessor...");

		if (annotations.size() < 1) {
			return true;
		}

		for (Element element : roundEnv.getElementsAnnotatedWith(WebServiceHelper.class)) {

			WebServiceHelper annotation = element.getAnnotation(WebServiceHelper.class);

			if (element.getKind() == ElementKind.INTERFACE) {

				TypeElement typeElement = (TypeElement) element;

				Helper helper = new Helper();
				helper.setImplementedClassModelName(new ClassModelName(typeElement.getQualifiedName().toString()));
				helper.setClassModelName(new ClassModelName(annotation.helperPackage(), annotation.name()));
				
				helper.setHelperPackageName(annotation.helperPackage());
				helper.setParserPackageName(annotation.parserPackage());
				

				for (Element childElement : typeElement.getEnclosedElements()) {

					if (childElement.getKind() == ElementKind.METHOD) {

						WebServiceMethod methodAnnotation = childElement.getAnnotation(WebServiceMethod.class);

						Method method = new Method();
						method.setUrl(methodAnnotation.url());
						method.setUrlFormatType(methodAnnotation.urlFormatType());
						ExecutableElement executableElement = (ExecutableElement) childElement;
						method.setName(executableElement.getSimpleName().toString());
						method.setReturnType(TypeUtils.getTypeName(executableElement.getReturnType()));
						for (VariableElement variableElement : executableElement.getParameters()) {
							method.getParameters().add(new ParameterTypeName(TypeUtils.getTypeName(variableElement.asType()), variableElement.toString()));
						}
						helper.getMethods().add(method);
					}
				}
				helpers.add(helper);
			}
		}


		for (Element element : roundEnv.getElementsAnnotatedWith(Model.class)) {

			if (element.getKind() == ElementKind.CLASS) {

				TypeElement typeElement = (TypeElement) element;

				List<Method> methods = new ArrayList<Method>();

				for (Element childElement : typeElement.getEnclosedElements()) {

					if (childElement.getKind() == ElementKind.METHOD && childElement.getSimpleName().toString().startsWith("set")) {

						ExecutableElement executableElement = (ExecutableElement) childElement;
						Method method = new Method();
						method.setName(executableElement.getSimpleName().toString());
						method.setReturnType(TypeUtils.getTypeName(executableElement.getReturnType()));
						for (VariableElement variableElement : executableElement.getParameters()) {

							method.getParameters().add(new ParameterTypeName(TypeUtils.getTypeName(variableElement.asType()), variableElement.toString()));
						}
						methods.add(method);
					}
				}

				models.put(typeElement.toString(), methods);
			}
		}
		logger.info("models = " + models);


		
		for (Helper helper : helpers) {
			TemplateGenerator.generateHelper(logger, processingEnv, cfg, helper);
			for (Method method : helper.getMethods()) {

				Parser parser = new Parser();
				parser.setGenericType(method.getReturnType());
				parser.setUrlFormatType(method.getUrlFormatType());
				parser.setClassModelName(new ClassModelName(helper.getParserPackageName(), method.getName()));
				TemplateGenerator.generateParser(logger, processingEnv, cfg, parser);
			}
		}
		return true;
	}
}
