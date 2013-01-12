package com.fozzy.apt.processor;

import java.io.Writer;
import java.util.ArrayList;
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
import javax.tools.JavaFileObject;

import com.fozzy.api.annotation.Model;
import com.fozzy.api.annotation.WebServiceHelper;
import com.fozzy.api.annotation.WebServiceMethod;
import com.fozzy.apt.model.ClassModel;
import com.fozzy.apt.model.Helper;
import com.fozzy.apt.model.Method;
import com.fozzy.apt.util.ProcessorLogger;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@SupportedAnnotationTypes({ "com.fozzy.api.annotation.Model", "com.fozzy.api.annotation.WebServiceHelper", "com.fozzy.api.annotation.WebServiceMethod" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class FozzyProcessor extends AbstractProcessor {

	private Configuration cfg = new Configuration();
	private ProcessorLogger logger;

	private List<Helper> helpers = new ArrayList<Helper>();
	private List<String> models = new ArrayList<String>();

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

		for (Element element : roundEnv.getElementsAnnotatedWith(Model.class)) {

			models.add(element.toString());
		}

		for (Element element : roundEnv.getElementsAnnotatedWith(WebServiceHelper.class)) {

			WebServiceHelper annotation = element.getAnnotation(WebServiceHelper.class);

			if (element.getKind() == ElementKind.INTERFACE) {

				TypeElement typeElement = (TypeElement) element;

				Helper helper = new Helper();
				helper.setImplementedClassModel(new ClassModel(typeElement.getQualifiedName().toString()));
				helper.setClassModel(new ClassModel(helper.getImplementedClassModel().getPackageName(), annotation.name()));

				for(Element childElement : typeElement.getEnclosedElements()){
					
					if(childElement.getKind() == ElementKind.METHOD){
						
						WebServiceMethod methodAnnotation = childElement.getAnnotation(WebServiceMethod.class);
						
						Method method = new Method();
						method.setUrl(methodAnnotation.url());
						
						ExecutableElement executableElement = (ExecutableElement) childElement;
						method.setReturnType(new ClassModel(executableElement.getReturnType().toString()));
						method.setName(executableElement.getSimpleName().toString());
						
						helper.getMethods().add(method);
						helper.getImports().add(method.getReturnType().getQualifiedClassName());
					}
				}
				
				helpers.add(helper);
			}
		}
		
		generateHelpers(helpers);
		
		for (Element element : roundEnv.getElementsAnnotatedWith(Model.class)) {
			
			if(element.getKind() == ElementKind.CLASS){
			
				
			}
		}
		
		return true;
	}

	private static final String HELPER_TEMPLATE = "Helper.ftl";

	public void generateHelpers(List<Helper> helpers) {

		for (Helper helper : helpers) {

			logger.info("Generating helper = " + helper);

			JavaFileObject file;
			try {
				file = processingEnv.getFiler().createSourceFile(helper.getClassModel().getQualifiedClassName());
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
