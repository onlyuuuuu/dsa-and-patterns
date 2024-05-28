package com.onlyu.tools.impl.templates;

import com.google.gson.stream.JsonReader;
import com.onlyu.tools.intf.Case;
import com.onlyu.tools.intf.Parser;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public abstract class JsonParserTemplate<IT, ET> implements Parser<IT, ET>
{
    private final String _inputFieldName;
    private final String _expecetedFieldName;

    private JsonParserTemplate()
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
        List<Case<IT, ET>> cases = new ArrayList<>();
        for (File file : files)
        {
            Scanner scanner = null;
            try
            {
                scanner = new Scanner(file);
                if (!scanner.hasNext())
                    continue;
                String firstCharacter = scanner.next();
                boolean isArray = false;
                if ("[".equals(firstCharacter))
                    isArray = true;
                JsonReader reader = new JsonReader(new FileReader(file));
                if (!isArray)
                {
                    
                }
            }
            catch (Exception e)
            {
                System.err.printf("\nError parsing contents of %s\n", file.getAbsolutePath());
                e.printStackTrace();
            }
            finally
            {
                if (scanner != null)
                    scanner.close();
            }
        }
        return cases;
    }



}
