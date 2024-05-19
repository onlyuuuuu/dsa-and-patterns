package com.onlyu.utils;

import java.io.File;

public class TryRead
{
    public static void main(String[] args)
    {
        TestFileRetriever retriever = new DefaultTestFileRetriever();
        File testFile = retriever.getDefault();
        System.out.printf("\nIs this file exists? %b\n", testFile.exists());
        System.out.printf("\nWhere is this file? %s\n\n", testFile.getAbsolutePath());
    }
}
