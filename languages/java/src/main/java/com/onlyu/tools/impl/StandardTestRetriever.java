package com.onlyu.tools.impl;

import com.onlyu.tools.intf.FlexibleRetriever;
import com.onlyu.tools.intf.SingleRetriever;
import com.onlyu.tools.intf.StandardRetriever;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class StandardTestRetriever implements SingleRetriever, StandardRetriever, FlexibleRetriever
{
    private static final String DEFAULT_TEST_FILE_NAME_REGEX = "(test|case).*\\.(test|tests|case|txt|conf)";

    static final class TestFilenameFilter implements FilenameFilter
    {
        @Override
        public boolean accept(File dir, String name)
        {
            return name.matches(DEFAULT_TEST_FILE_NAME_REGEX);
        }
    }

    private static StandardTestRetriever INSTANCE;

    public static synchronized StandardTestRetriever getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new StandardTestRetriever();
        return INSTANCE;
    }

    @Override
    public File retrieve()
    {
        List<File> files = retrieveMultiple();
        return !files.isEmpty() ? files.get(0) : null;
    }

    @Override
    public List<File> retrieveMultiple()
    {
        return retrieveMultipleFrom(null);
    }

    @Override
    public List<File> retrieveMultipleFrom(File... files)
    {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        String className = elements[elements.length - 1].getClassName();
        String[] split = className.split("\\.");
        split = Arrays.copyOf(split, split.length - 1);
        String packagePath = sterilePathSeperators(String.join(File.separator, split), false);
        File invokedSourceDir = null;
        List<File> testFiles = new ArrayList<>();
        try
        {
            Class<?> main = Class.forName(className);
            URL location = main.getProtectionDomain().getCodeSource().getLocation();
            String sourceAbsolutePath = sterilePathSeperators(location.toURI().toString(), true);
            String fullPath = Paths.get(sourceAbsolutePath, packagePath).toString();
            invokedSourceDir = Paths.get(fullPath).toFile();
            final String productionStringSequence = File.separator + "production" + File.separator;
            final String testStringSequence = File.separator + "test" + File.separator;
            File secondarySearchDir;
            if (fullPath.contains(productionStringSequence) && !fullPath.contains(testStringSequence))
                secondarySearchDir = Paths.get(fullPath.replace(productionStringSequence, testStringSequence)).toFile();
            else if (!fullPath.contains(productionStringSequence) && fullPath.contains(testStringSequence))
                secondarySearchDir = Paths.get(fullPath.replace(testStringSequence, productionStringSequence)).toFile();
            else
                secondarySearchDir = null;
            List<File> first = Arrays.stream(invokedSourceDir.listFiles(new TestFilenameFilter())).toList();
            List<File> second = new ArrayList<>();
            if (secondarySearchDir != null && secondarySearchDir.listFiles(new TestFilenameFilter()) != null)
            {
                File[] secondaryTestFiles = secondarySearchDir.listFiles(new TestFilenameFilter());
                if (secondaryTestFiles != null && secondaryTestFiles.length > 0)
                    second = Arrays.stream(secondaryTestFiles).toList();
            }
            testFiles.addAll(first);
            testFiles.addAll(second);
        }
        catch (ClassNotFoundException e)
        {
            System.err.printf("\nClass %s not found. Encountered [%s]\n", className, e.getMessage());
            return Collections.emptyList();
        }
        catch (URISyntaxException e)
        {
            System.err.printf("\nError parsing source path. Encountered [%s]\n", e.getMessage());
            return Collections.emptyList();
        }
        return testFiles;
    }

    private String sterilePathSeperators(String path, boolean removeFirstSlash)
    {
        path = path.replaceAll("file:[\\/\\\\]*", "");
        path = path.replace("/", File.separator);
        path = path.replace("\\", File.separator);
        path = path.replace(System.getProperty("user.dir").replaceFirst("^/", ""), "");
        if (!removeFirstSlash)
            return path;
        path = path.replaceFirst("\\/", "");
        path = path.replaceFirst("\\\\", "");
        return path;
    }
}