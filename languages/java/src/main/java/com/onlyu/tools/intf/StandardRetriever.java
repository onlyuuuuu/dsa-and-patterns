package com.onlyu.tools.intf;

import java.io.File;
import java.util.List;

public interface StandardRetriever extends Retriever
{
    List<File> retrieveMultiple();
}
