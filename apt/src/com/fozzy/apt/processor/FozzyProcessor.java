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
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.JavaFileObject;

import com.fozzy.api.annotation.Model;
import com.fozzy.api.annotation.WebServiceHelper;
import com.fozzy.api.annotation.WebServiceMethod;
import com.fozzy.apt.model.ClassModel;
import com.fozzy.apt.model.Helper;
import com.fozzy.apt.util.ProcessorLogger;
import com.fozzy.apt.util.StringUtils;
import com.sun.tools.javac.code.Type.PackageType;

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

			TypeElement typeElement = (TypeElement) element;

			if (typeElement.getKind() == ElementKind.INTERFACE) {

				logger.info("typeElement interfaces = " + typeElement.getInterfaces());
				logger.info("typeElement kind = " + typeElement.getKind());
				logger.info("typeElement qualified name = " + typeElement.getQualifiedName());
				logger.info("typeElement nesting kind = " + typeElement.getNestingKind());
				logger.info("typeElement getEnclosedElements = " + typeElement.getEnclosedElements());
				logger.info("typeElement getEnclosingElement = " + typeElement.getEnclosingElement());

				Helper helper = new Helper();
				helper.setImplementedClassModel(new ClassModel(typeElement.getQualifiedName().toString()));
				helper.setClassModel(new ClassModel(helper.getImplementedClassModel().getPackageName(), annotation.name()));				
				helper.getImports().add(helper.getImplementedClassModel().getQualifiedClassName());
				
				helpers.add(helper);
			}
		}

		for (Element element : roundEnv.getElementsAnnotatedWith(WebServiceMethod.class)) {
			/*
			 * logger.info("element method = " + element);
			 * logger.info("element method element.getEnclosedElements() = " +
			 * element.getEnclosedElements());
			 * logger.info("element method element.getEnclosingElement() = " +
			 * element.getEnclosingElement());
			 * logger.info("element method element.getModifiers() = " +
			 * element.getModifiers());
			 * logger.info("element method element.getKind() = " +
			 * element.getKind()); logger.info("element method simple name = " +
			 * element.getSimpleName());
			 * 
			 * WebServiceMethod annotation =
			 * element.getAnnotation(WebServiceMethod.class);
			 * 
			 * logger.info("element method annotation url = " +
			 * annotation.url());
			 * logger.info("element method annotation resId = " +
			 * annotation.resourceId());
			 */
		}

		generateHelpers(helpers);
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
