package com.onlyu.tools.intf;

import java.io.File;
import java.util.List;

public interface FlexibleRetriever extends Retriever
{
    List<File> retrieveMultipleFrom(File... directories);
}
