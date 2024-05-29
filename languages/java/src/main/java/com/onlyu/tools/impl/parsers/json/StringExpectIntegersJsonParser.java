package com.onlyu.tools.impl.parsers.json;

import com.google.gson.stream.JsonReader;
import com.onlyu.tools.impl.displayables.DisplayableArrayList;
import com.onlyu.tools.impl.templates.JsonParserTemplate;

import java.io.IOException;

public class StringExpectIntegersJsonParser extends JsonParserTemplate<String, DisplayableArrayList<Integer>>
{
    public StringExpectIntegersJsonParser()
    {
        super();
    }

    private static StringExpectIntegersJsonParser _INSTANCE;

    public static synchronized StringExpectIntegersJsonParser getInstance()
    {
        if (_INSTANCE == null)
            _INSTANCE = new StringExpectIntegersJsonParser();
        return _INSTANCE;
    }

    @Override
    public String readInput(JsonReader reader) throws IOException
    {
        return reader.nextString();
    }

    @Override
    public DisplayableArrayList<Integer> readExpected(JsonReader reader) throws IOException
    {
        DisplayableArrayList<Integer> integers = new DisplayableArrayList<>();
        while (reader.hasNext())
            integers.add(reader.nextInt());
        return integers;
    }
}
