package com.onlyu.tools.impl.templates;

import com.google.gson.stream.JsonReader;
import com.onlyu.tools.impl.StandardCase;
import com.onlyu.tools.intf.Case;
import com.onlyu.tools.intf.Parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public abstract class JsonParserTemplate<IT, ET> implements Parser<IT, ET>
{
    private static final int _OBJECT_TYPE = 1;
    private static final int _ARRAY_TYPE = 2;
    private static final int _VALUE_TYPE = 3;

    private final String _inputFieldName;
    private final String _expecetedFieldName;

    protected JsonParserTemplate()
    {
        this._inputFieldName = "input";
        this._expecetedFieldName = "expected";
    }

    public JsonParserTemplate(String inputFieldName, String expectedFieldName)
    {
        this._inputFieldName = inputFieldName;
        this._expecetedFieldName = expectedFieldName;
    }

    @Override
    public List<Case<IT, ET>> parse(File... files)
    {
        return parse(Arrays.stream(files).toList());
    }

    @Override
    public List<Case<IT, ET>> parse(Collection<File> files)
    {
        if (files == null || files.isEmpty())
            return Collections.emptyList();
        List<Case<IT, ET>> cases = new ArrayList<>();
        for (File file : files)
        {
            try
            {
                Case<IT, ET> _case;
                JsonReader reader = new JsonReader(new FileReader(file));
                int type = begin(reader);
                if (type == _VALUE_TYPE)
                    continue;
                if (type == _OBJECT_TYPE)
                {
                    _case = readSingleCase(reader, false);
                    if (_case != null)
                        cases.add(_case);
                }
                else
                {
                    while (reader.hasNext())
                    {
                        _case = readSingleCase(reader, true);
                        if (_case != null)
                            cases.add(_case);
                    }
                }
                end(reader, type);
            }
            catch (Exception e)
            {
                System.err.printf("\nError parsing contents of %s\n", file.getAbsolutePath());
                e.printStackTrace();
            }
        }
        return cases;
    }

    public abstract IT readInput(JsonReader reader) throws IOException;
    public abstract ET readExpected(JsonReader reader) throws IOException;

    private Case<IT, ET> readSingleCase(JsonReader reader, boolean withBeginEnd) throws Exception
    {
        if (withBeginEnd)
            reader.beginObject();
        IT input = null;
        ET expected = null;
        int type;
        while (reader.hasNext())
        {
            String field = reader.nextName();
            if (_inputFieldName.equals(field))
            {
                type = begin(reader);
                input = readInput(reader);
                end(reader, type);
                continue;
            }
            if (_expecetedFieldName.equals(field))
            {
                type = begin(reader);
                expected = readExpected(reader);
                end(reader, type);
            }
        }
        if (withBeginEnd)
            reader.endObject();
        if (input == null || expected == null)
            return null;
        StandardCase<IT, ET> _case = new StandardCase<>();
        _case.setInput(input);
        _case.setExpected(expected);
        return _case;
    }

    private int begin(JsonReader reader) throws Exception
    {
        try
        {
            reader.beginObject();
            return _OBJECT_TYPE;
        }
        catch (Exception e)
        {
            if (e.getMessage().startsWith("Expected BEGIN_OBJECT but was BEGIN_ARRAY"))
            {
                reader.beginArray();
                return _ARRAY_TYPE;
            }
            else
                return _VALUE_TYPE;
        }
    }

    private int end(JsonReader reader, int type) throws Exception
    {
        if (type == _OBJECT_TYPE)
            reader.endObject();
        else if (type == _ARRAY_TYPE)
            reader.endArray();
        return type;
    }

}
