package com.onlyu.tools.impl;

import com.onlyu.tools.intf.Case;
import com.onlyu.tools.intf.MatchingTask;

public class StandardCase<IT, ET> implements Case<IT, ET>
{
    private IT input;
    private ET expected;

    public StandardCase(IT input, ET expected)
    {
        this.input = input;
        this.expected = expected;
    }

    protected StandardCase<IT, ET> setInput(IT value)
    {
        this.input = value;
        return this;
    }

    protected StandardCase<IT, ET> setExpected(ET value)
    {
        this.expected = value;
        return this;
    }

    @Override
    public IT getInput()
    {
        return this.input;
    }

    @Override
    public ET getExpected()
    {
        return this.expected;
    }

    @Override
    public boolean matches(ET payload)
    {
        return this.expected.equals(payload);
    }

    @Override
    public boolean matches(ET payload, MatchingTask task)
    {
        boolean isMatch = this.expected.equals(payload);
        if (isMatch)
            task.onMatch();
        else
            task.onMismatch();
        return isMatch;
    }
}
