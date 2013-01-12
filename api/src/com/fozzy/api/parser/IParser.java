
package com.fozzy.api.parser;

import java.io.InputStream;

public interface IParser<T> {

    public void parse(InputStream is);

    public T getResult();
}
