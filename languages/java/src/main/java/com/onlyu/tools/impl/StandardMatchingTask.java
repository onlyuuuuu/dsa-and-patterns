package com.onlyu.tools.impl;

import com.onlyu.tools.intf.Case;
import com.onlyu.tools.intf.MatchingTask;

public final class StandardMatchingTask<IT, ET> implements MatchingTask<IT, ET>
{
    private static StandardMatchingTask INSTANCE;

    public static synchronized StandardMatchingTask getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new StandardMatchingTask();
        return INSTANCE;
    }

    @Override
    public void onMatch(Case<IT, ET> testCase, ET actual)
    {
        System.out.printf("PASSED | Input: %s | Expected: %s | Actual: %s\n", String.valueOf(testCase.getInput()), String.valueOf(testCase.getExpected()), String.valueOf(actual));
    }

    @Override
    public void onMismatch(Case<IT, ET> testCase, ET actual)
    {
        System.err.printf("FAILED | Input: %s | Expected: %s | Actual: %s\n", String.valueOf(testCase.getInput()), String.valueOf(testCase.getExpected()), String.valueOf(actual));
    }
}
