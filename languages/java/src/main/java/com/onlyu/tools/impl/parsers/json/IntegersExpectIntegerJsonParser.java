package com.onlyu.tools.impl.parsers.json;

import com.google.gson.stream.JsonReader;
import com.onlyu.tools.impl.displayables.DisplayableArrayList;
import com.onlyu.tools.impl.templates.JsonParserTemplate;

import java.io.IOException;

public class IntegersExpectIntegerJsonParser extends JsonParserTemplate<DisplayableArrayList<Integer>, Integer>
{
    public IntegersExpectIntegerJsonParser()
    {
        super();
    }

    private static IntegersExpectIntegerJsonParser _INSTANCE;

    public static synchronized IntegersExpectIntegerJsonParser getInstance()
    {
        if (_INSTANCE == null)
            _INSTANCE = new IntegersExpectIntegerJsonParser();
        return _INSTANCE;
    }

    @Override
    public DisplayableArrayList<Integer> readInput(JsonReader reader) throws IOException
    {
        DisplayableArrayList<Integer> integers = new DisplayableArrayList<>();
        while (reader.hasNext())
            integers.add(reader.nextInt());
        return integers;
    }

    @Override
    public Integer readExpected(JsonReader reader) throws IOException
    {
        return reader.nextInt();
    }
}
