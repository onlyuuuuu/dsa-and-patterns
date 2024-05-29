package com.onlyu.utils;

public class ArrayUtils
{
    public static String toString(int[] arr)
    {
        String output = "";
        for (int i = 0; i < arr.length; i++)
            output += arr[i] + ", ";
        return !output.isEmpty() ? output.substring(0, output.length() - 2) : output;
    }
}
