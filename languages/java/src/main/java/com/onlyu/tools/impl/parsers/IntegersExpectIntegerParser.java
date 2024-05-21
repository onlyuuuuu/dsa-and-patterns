package com.onlyu.tools.impl.parsers;

import com.onlyu.tools.impl.displayables.DisplayableIntegers;
import com.onlyu.tools.impl.templates.StandardParserTemplate;

import java.util.Arrays;

public class IntegersExpectIntegerParser extends StandardParserTemplate<DisplayableIntegers, Integer>
{
    private final String _delimiterRegex;

    public IntegersExpectIntegerParser(String delimiterRegex)
    {
        this._delimiterRegex = delimiterRegex;
    }

    private static IntegersExpectIntegerParser _INSTANCE;

    public static synchronized IntegersExpectIntegerParser getInstance()
    {
        if (_INSTANCE == null)
            _INSTANCE = new IntegersExpectIntegerParser("[ ]*,[ ]*");
        return _INSTANCE;
    }

    @Override
    protected DisplayableIntegers parseInput(String input)
    {
        return new DisplayableIntegers
        (
            Arrays.stream(input.split(_delimiterRegex))
                .mapToInt(Integer::parseInt)
                .boxed()
                .toList()
        );
    }

    @Override
    protected Integer parseExpected(String expected)
    {
        return Integer.parseInt(expected);
    }
}
