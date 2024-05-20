package com.onlyu.tools.intf;

public interface Case<IT, ET>
{
    IT getInput();
    ET getExpected();
    boolean matches(ET payload);
    boolean matches(ET payload, MatchingTask task);
}
