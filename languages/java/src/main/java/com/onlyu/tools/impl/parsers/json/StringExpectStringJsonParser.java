package com.onlyu.tools.impl.parsers.json;

import com.google.gson.stream.JsonReader;
import com.onlyu.tools.impl.templates.JsonParserTemplate;

import java.io.IOException;

public class StringExpectStringJsonParser extends JsonParserTemplate<String, String>
{
    public StringExpectStringJsonParser()
    {
        super();
    }

    private static StringExpectStringJsonParser _INSTANCE;

    public static synchronized StringExpectStringJsonParser getInstance()
    {
        if (_INSTANCE == null)
            _INSTANCE = new StringExpectStringJsonParser();
        return _INSTANCE;
    }

    @Override
    public String readInput(JsonReader reader) throws IOException
    {
        return reader.nextString();
    }

    @Override
    public String readExpected(JsonReader reader) throws IOException
    {
        return reader.nextString();
    }
}
