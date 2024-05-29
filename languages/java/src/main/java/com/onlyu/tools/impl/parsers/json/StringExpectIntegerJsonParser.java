package com.onlyu.tools.impl.parsers.json;

import com.google.gson.stream.JsonReader;
import com.onlyu.tools.impl.templates.JsonParserTemplate;

import java.io.IOException;

public class StringExpectIntegerJsonParser extends JsonParserTemplate<String, Integer>
{
    public StringExpectIntegerJsonParser()
    {
        super();
    }

    private static StringExpectIntegerJsonParser _INSTANCE;

    public static synchronized StringExpectIntegerJsonParser getInstance()
    {
        if (_INSTANCE == null)
            _INSTANCE = new StringExpectIntegerJsonParser();
        return _INSTANCE;
    }

    @Override
    public String readInput(JsonReader reader) throws IOException
    {
        return reader.nextString();
    }

    @Override
    public Integer readExpected(JsonReader reader) throws IOException
    {
        return reader.nextInt();
    }
}
