package com.onlyu.tools.impl.parsers.json;

import com.onlyu.tools.impl.StandardTestRetriever;
import com.onlyu.tools.impl.displayables.DisplayableArrayList;
import com.onlyu.tools.intf.Case;

import java.util.List;

public class IntegersExpectIntegerJsonParserTest
{
    public static void main(String[] args)
    {
        List<Case<DisplayableArrayList<Integer>, Integer>> testCases = IntegersExpectIntegerJsonParser.getInstance()
            .parse(StandardTestRetriever.getInstance().retrieveMultiple());
        for (Case<DisplayableArrayList<Integer>, Integer> testCase : testCases)
            System.out.printf("\n" + testCase + "\n");
        System.out.printf("\n");
    }
}
