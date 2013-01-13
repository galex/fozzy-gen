package com.fozzy.apt.util;

import java.io.Writer;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;

import com.fozzy.apt.model.Helper;
import com.fozzy.apt.model.Parser;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateGenerator {

	private static final String HELPER_TEMPLATE = "Helper.ftl";
	private static final String PARSER_TEMPLATE = "JsonParser.ftl";

	public static void generateHelper(ProcessorLogger logger, ProcessingEnvironment processingEnv, Configuration cfg, Helper helper) {

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

	public static void generateParser(ProcessorLogger logger, ProcessingEnvironment processingEnv, Configuration cfg, Parser parser) {

		logger.info("Generating parser = " + parser.getClassModelName().getClassName());

		JavaFileObject file;
		try {
			file = processingEnv.getFiler().createSourceFile(parser.getClassModelName().getQualifiedClassName());
			Writer out = file.openWriter();
			Template t = cfg.getTemplate(PARSER_TEMPLATE);
			t.process(parser, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
