package com.onlyu.tools.intf;

public interface MatchingTask<IT, ET>
{
    void onMatch(Case<IT, ET> testCase, ET actual);
    void onMismatch(Case<IT, ET> testCase, ET actual);
}
