package com.onlyu.tools.intf;

public interface Case<IT, ET>
{
    IT getInput();
    ET getExpected();
    boolean matches(ET actual);
    boolean matches(ET actual, MatchingTask<IT, ET> task);
}
