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
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import com.fozzy.api.annotation.WebServiceHelper;
import com.fozzy.apt.model.Helper;
import com.fozzy.apt.util.ProcessorLogger;
import com.fozzy.apt.util.StringUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@SupportedAnnotationTypes({ "com.fozzy.api.annotation.Model", "com.fozzy.api.annotation.WebServiceHelper", "com.fozzy.api.annotation.WebServiceMethod" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class FozzyProcessor extends AbstractProcessor {

	private Configuration cfg = new Configuration();
	private ProcessorLogger logger;

	private List<Helper> helpers = new ArrayList<Helper>();
	private List<Class<?>> models = new ArrayList<Class<?>>();

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

			Helper helper = new Helper();
			helper.setImplementingInterface(element.getSimpleName().toString());
			helper.setName(annotation.name());
			helper.getImports().add(element.toString());
			
			
			logger.info("element toString = " + element.toString());
			logger.info("package = " + StringUtils.getPackageName(element.toString()));
			
			helper.setPackage(StringUtils.getPackageName(element.toString()));
			
			helpers.add(helper);
		}

		logger.info("Helpers = " + helpers);
		
		generateHelpers(helpers);
		
		logger.info("Models = " + models);
		
		return true;
	}
	
	private static final String HELPER_TEMPLATE = "Helper.ftl";

	public void generateHelpers(List<Helper> helpers) {

		for (Helper helper : helpers) {
			
			logger.info("Generating helper = "+ helper);

			JavaFileObject file;
			try {
				file = processingEnv.getFiler().createSourceFile(StringUtils.getCompleteClassName(helper.getPackage(), helper.getName()));
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
