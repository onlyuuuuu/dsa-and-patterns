package com.onlyu.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultTestFileRetriever implements TestFileRetriever
{
    private static final String DEFAULT_TEST_FILE_NAME = "tests.txt";

    @Override
    public File getDefault()
    {
        return from().get(0);
    }

    @Override
    public List<File> from()
    {
        return from(null);
    }

    @Override
    public List<File> from(File... files)
    {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String className = elements[elements.length - 1].getClassName();
        String[] split = className.split("\\.");
        split = Arrays.copyOf(split, split.length - 1);
        String packagePath = String.join(File.separator, split);
        File test = null;
        try
        {
            Class<?> main = Class.forName(className);
            URL location = main.getProtectionDomain().getCodeSource().getLocation();
            test = Paths
                .get(location.toURI().toString().replace("file:", "") + packagePath + File.separator + DEFAULT_TEST_FILE_NAME)
                .toFile();
        }
        catch (ClassNotFoundException e)
        {
            System.err.printf("\nClass %s not found. Encountered [%s]\n", className, e.getMessage());
        }
        catch (URISyntaxException e)
        {
            System.err.printf("\nError parsing source path. Encountered [%s]\n", e.getMessage());
        }
        return Collections.singletonList(test);
    }
}
