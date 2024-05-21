package com.onlyu.tools.impl.templates;

import com.onlyu.tools.intf.Case;
import com.onlyu.tools.intf.Parser;

import java.io.File;
import java.util.*;

public abstract class ParserTemplate<IT, ET> implements Parser<IT, ET>
{
    private final int _lineNumberPerCase;

    private ParserTemplate()
    {
        this._lineNumberPerCase = 2;
    }

    public ParserTemplate(int lineNumberPerCase)
    {
        this._lineNumberPerCase = lineNumberPerCase;
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
        String[] lines = new String[_lineNumberPerCase];
        Scanner scanner = null;
        for (File file : files)
        {
            try
            {
                int index = 0;
                scanner = new Scanner(file);
                while (scanner.hasNextLine())
                {
                    String line = scanner.nextLine();
                    if (shouldIgnore(line))
                        continue;
                    if (index == _lineNumberPerCase)
                    {
                        Case<IT, ET> _case = toCase(lines);
                        if (_case != null)
                            cases.add(_case);
                        lines = new String[_lineNumberPerCase];
                        index = 0;
                    }
                    lines[index++] = line;
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

    protected boolean shouldIgnore(String line)
    {
        return line.trim().length() == 0;
    }

    protected abstract Case<IT, ET> toCase(String... line);

}
