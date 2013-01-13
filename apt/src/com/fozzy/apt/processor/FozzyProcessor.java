package com.fozzy.apt.processor;

import java.io.Writer;
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
import javax.tools.JavaFileObject;

import com.fozzy.api.annotation.Model;
import com.fozzy.api.annotation.WebServiceHelper;
import com.fozzy.api.annotation.WebServiceMethod;
import com.fozzy.apt.model.ClassModelName;
import com.fozzy.apt.model.Helper;
import com.fozzy.apt.model.Method;
import com.fozzy.apt.model.ParameterTypeName;
import com.fozzy.apt.util.ProcessorLogger;
import com.fozzy.apt.util.TypeUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@SupportedAnnotationTypes({ "com.fozzy.api.annotation.Model", "com.fozzy.api.annotation.WebServiceHelper", "com.fozzy.api.annotation.WebServiceMethod" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class FozzyProcessor extends AbstractProcessor {

	private Configuration cfg = new Configuration();
	private ProcessorLogger logger;

	private List<Helper> helpers = new ArrayList<Helper>();
	private HashMap<String,List<Method>> models = new HashMap<String,List<Method>>();

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		this.cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/res"));
		this.logger = new ProcessorLogger(processingEnv.getMessager());
		logger.info("Running FozzyProcessor...");

		// Exit early if no annotations in this round so we don't overwrite the
		// env file
		if (annotations.size() < 1) {
			return true;
		}
	
		for (Element element : roundEnv.getElementsAnnotatedWith(WebServiceHelper.class)) {

			WebServiceHelper annotation = element.getAnnotation(WebServiceHelper.class);

			if (element.getKind() == ElementKind.INTERFACE) {

				TypeElement typeElement = (TypeElement) element;

				Helper helper = new Helper();
				helper.setImplementedClassModelName(new ClassModelName(typeElement.getQualifiedName().toString()));
				helper.setClassModelName(new ClassModelName(helper.getImplementedClassModelName().getPackageName(), annotation.name()));

				for (Element childElement : typeElement.getEnclosedElements()) {

					if (childElement.getKind() == ElementKind.METHOD) {

						WebServiceMethod methodAnnotation = childElement.getAnnotation(WebServiceMethod.class);

						Method method = new Method();
						method.setUrl(methodAnnotation.url());
						ExecutableElement executableElement = (ExecutableElement) childElement;
						method.setName(executableElement.getSimpleName().toString());
						method.setReturnType(TypeUtils.getTypeName(executableElement.getReturnType()));
						for(VariableElement variableElement : executableElement.getParameters()){							
							method.getParameters().add(new ParameterTypeName(TypeUtils.getTypeName(variableElement.asType()), variableElement.toString()));
						}
						helper.getMethods().add(method);
					}
				}
				helpers.add(helper);
			}
		}

		generateHelpers(helpers);

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
						for(VariableElement variableElement : executableElement.getParameters()){
														
							method.getParameters().add(new ParameterTypeName(TypeUtils.getTypeName(variableElement.asType()), variableElement.toString()));
						}
						methods.add(method);
						
					}
				}
				
				models.put(typeElement.toString(), methods);
			}
		}
		
		logger.info("models = " + models);

		return true;
	}

	private static final String HELPER_TEMPLATE = "Helper.ftl";

	public void generateHelpers(List<Helper> helpers) {

		for (Helper helper : helpers) {

			logger.info("Generating helper = " + helper.getClassModelName().getClassName());

			JavaFileObject file;
			try {
				file = processingEnv.getFiler().createSourceFile(helper.getClassModelName().getQualifiedClassName());
				Writer out = file.openWriter();
				Template t = cfg.getTemplate(HELPER_TEMPLATE);
				t.process(helper, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
