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
                JsonReader reader = new JsonReader(new FileReader(file));
                IT input = null;
                ET expected = null;
                boolean io1 = begin(reader);
                boolean io2;
                // TODO: Need to fix this logic
                while (reader.hasNext())
                {
                    if (!io1)
                        io1 = begin(reader);
                    String field = reader.nextName();
                    if (_inputFieldName.equals(field))
                    {
                        io2 = begin(reader);
                        input = readInput(reader);
                        end(reader, io2);
                    }
                    else if (_expecetedFieldName.equals(field))
                    {
                        io2 = begin(reader);
                        expected = readExpected(reader);
                        end(reader, io2);
                    }
                }
                if (input == null || expected == null)
                {
                    end(reader, io1);
                    continue;
                }
                StandardCase<IT, ET> _case = new StandardCase<>();
                _case.setInput(input);
                _case.setExpected(expected);
                cases.add(_case);
                end(reader, io1);
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

    private boolean begin(JsonReader reader) throws Exception
    {
        try
        {
            reader.beginObject();
            return true;
        }
        catch (Exception e)
        {
            reader.beginArray();
            return false;
        }
    }

    private boolean end(JsonReader reader, boolean isObject) throws Exception
    {
        if (isObject)
            reader.endObject();
        else
            reader.endArray();
        return isObject;
    }

}
