package com.onlyu.tools.impl.parsers.normal;

import com.onlyu.tools.impl.displayables.DisplayableArrayList;
import com.onlyu.tools.impl.templates.StandardParserTemplate;

import java.util.Arrays;

public class IntegersExpectIntegerNormalParser extends StandardParserTemplate<DisplayableArrayList<Integer>, Integer>
{
    private final String _delimiterRegex;

    public IntegersExpectIntegerNormalParser(String delimiterRegex)
    {
        this._delimiterRegex = delimiterRegex;
    }

    private static IntegersExpectIntegerNormalParser _INSTANCE;

    public static synchronized IntegersExpectIntegerNormalParser getInstance()
    {
        if (_INSTANCE == null)
            _INSTANCE = new IntegersExpectIntegerNormalParser("[ ]*,[ ]*");
        return _INSTANCE;
    }

    @Override
    protected DisplayableArrayList<Integer> parseInput(String input)
    {
        return new DisplayableArrayList<>
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
