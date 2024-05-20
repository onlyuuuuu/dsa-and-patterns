package com.onlyu.tools.intf;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface Parser<IT, ET>
{
    List<Case<IT, ET>> parse(File... files);
    List<Case<IT, ET>> parse(Collection<File> files);
}
