package com.onlyu.tools.impl;

import com.onlyu.tools.intf.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class StandardParserTemplate<IT, ET> implements Parser<IT, ET>
{
    @Override
    public List<Case<IT, ET>> parse(File... files)
    {
        return parse(Arrays.stream(files).toList());
    }

    @Override
    public List<Case<IT, ET>> parse(Collection<File> files)
    {
        for (File file : files)
        {
            Scanner scanner = null;
            try
            {
                scanner = new Scanner(file);
                while (scanner.hasNextLine())
                {
                    System.out.printf(scanner.next() + "\n");
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
        return List.of();
    }
}
