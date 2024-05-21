package com.onlyu.tools.impl.templates;

import com.onlyu.tools.intf.Case;
import com.onlyu.tools.intf.Parser;

import java.io.File;
import java.util.*;

public abstract class ParserTemplate<IT, ET> implements Parser<IT, ET>
{
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
                while (scanner.hasNextLine())
                {
                    String line = scanner.next();
                    if (shouldIgnore(line))
                        continue;
                    cases.add(toCase(line));
                }
            }
            catch (Exception e)
            {
                System.err.printf("Error parsing contents of %s", file.getAbsolutePath());
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

    protected abstract Case<IT, ET> toCase(String line);

}
