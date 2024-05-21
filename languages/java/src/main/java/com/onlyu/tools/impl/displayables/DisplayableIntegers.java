package com.onlyu.tools.impl.displayables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayableIntegers extends ArrayList<Integer>
{
    public DisplayableIntegers(Integer... integers)
    {
        this(Arrays.stream(integers).toList());
    }

    public DisplayableIntegers(List<Integer> integers)
    {
        this.addAll(integers);
    }

    @Override
    public String toString()
    {
        String display = "";
        for (Integer integer : this)
            display += integer + ", ";
        if (!display.isEmpty())
            display = display.substring(0, display.length() - 2);
        return display;
    }
}
