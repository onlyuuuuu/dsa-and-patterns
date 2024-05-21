package com.onlyu.tools.impl.templates;

import com.onlyu.tools.intf.Case;

public abstract class StandardParserTemplate<IT, ET> extends ParserTemplate<IT, ET>
{
    private final static String REGEX = "";

    @Override
    protected boolean shouldIgnore(String line)
    {
        return line.matches(REGEX);
    }

    @Override
    protected Case<IT, ET> toCase(String line)
    {
        return null;
    }

}
