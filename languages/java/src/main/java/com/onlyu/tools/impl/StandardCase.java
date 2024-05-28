package com.onlyu.tools.impl;

import com.onlyu.tools.intf.Case;
import com.onlyu.tools.intf.MatchingTask;

public class StandardCase<IT, ET> implements Case<IT, ET>
{
    private IT _input;
    private ET _expected;

    public void setInput(IT value)
    {
        this._input = value;
    }

    public void setExpected(ET value)
    {
        this._expected = value;
    }

    @Override
    public IT getInput()
    {
        return this._input;
    }

    @Override
    public ET getExpected()
    {
        return this._expected;
    }

    @Override
    public boolean matches(ET actual)
    {
        return this._expected.equals(actual);
    }

    @Override
    public boolean matches(ET actual, MatchingTask<IT, ET> task)
    {
        boolean isMatch = this._expected.equals(actual);
        if (isMatch)
            task.onMatch(this, actual);
        else
            task.onMismatch(this, actual);
        return isMatch;
    }

    @Override
    public String toString()
    {
        return "StandardCase" +
                "{ " +
                "_input = " + _input.toString() + ", " +
                "_expected = " + _expected.toString() + " " +
                "}";
    }
}
