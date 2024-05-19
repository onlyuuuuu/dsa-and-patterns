package com.onlyu.utils;

import java.io.File;
import java.util.List;

public interface TestFileRetriever
{
    File getDefault();
    List<File> from();
    List<File> from(File... files);
}


