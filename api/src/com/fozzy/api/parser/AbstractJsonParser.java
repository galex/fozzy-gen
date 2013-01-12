package com.fozzy.api.parser;

import java.io.InputStream;

import org.json.JSONTokener;

import com.fozzy.api.util.FileUtils;

public abstract class AbstractJsonParser<T, V> {

	@SuppressWarnings("unchecked")
	public T parse(InputStream is) throws Exception {
		return parseJson((V) new JSONTokener(FileUtils.convertStreamToString(is)).nextValue());
	}

	public abstract T parseJson(V json) throws Exception;
}
