package com.onlyu.tools;

import com.onlyu.tools.impl.StandardTestRetriever;
import com.onlyu.tools.intf.SingleRetriever;

import java.io.File;

public class StandardTestRetrieverTest
{
    public static void main(String[] args)
    {
        SingleRetriever retriever = new StandardTestRetriever();
        File testFile = retriever.retrieve();
        System.out.printf("\nIs this test file exists? %b\n", testFile.exists());
        System.out.printf("\nWhere is this test file (from %s)? %s\n", System.getProperty("user.dir"), testFile.getPath());
        System.out.printf("\nWhere is this test file (absolute path)? %s\n\n", testFile.getAbsolutePath());
    }
}
