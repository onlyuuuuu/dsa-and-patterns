package com.onlyu.tools.impl.templates;

import com.onlyu.tools.impl.StandardCase;
import com.onlyu.tools.intf.Case;

public abstract class StandardParserTemplate<IT, ET> extends ParserTemplate<IT, ET>
{
    private final static String _INPUT_PREFIX_EXPRESSION = "^Input[ ]*:[ ]*";
    private final static String _OUTPUT_PREFIX_EXPRESSION = "^Expected[ ]*:[ ]*";

    public StandardParserTemplate()
    {
        super(2);
    }

    @Override
    protected boolean shouldIgnore(String line)
    {
        boolean ignore = super.shouldIgnore(line);
        if (ignore)
            return ignore;
        if (line.matches(_INPUT_PREFIX_EXPRESSION + ".*"))
            return false;
        if (line.matches(_OUTPUT_PREFIX_EXPRESSION + ".*"))
            return false;
        return true;
    }

    @Override
    protected Case<IT, ET> toCase(String... lines)
    {
        IT input = parseInput(lines[0].replaceFirst(_INPUT_PREFIX_EXPRESSION, ""));
        ET expected = parseExpected(lines[1].replaceFirst(_OUTPUT_PREFIX_EXPRESSION, ""));
        StandardCase<IT, ET> _case = new StandardCase<>();
        _case.setInput(input);
        _case.setExpected(expected);
        return _case;
    }

    protected abstract IT parseInput(String input);
    protected abstract ET parseExpected(String expected);

}
