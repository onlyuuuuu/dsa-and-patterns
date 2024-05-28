package com.onlyu.tools.impl.displayables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayableArrayList<T> extends ArrayList<T>
{
    public DisplayableArrayList(T... items)
    {
        this(Arrays.stream(items).toList());
    }

    public DisplayableArrayList(List<T> items)
    {
        this.addAll(items);
    }

    @Override
    public String toString()
    {
        String display = "";
        for (T item : this)
            display += item.toString() + ", ";
        if (!display.isEmpty())
            display = display.substring(0, display.length() - 2);
        return display;
    }
}
