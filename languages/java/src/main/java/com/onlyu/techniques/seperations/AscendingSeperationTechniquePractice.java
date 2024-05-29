package com.onlyu.techniques.seperations;

import com.onlyu.utils.ArrayUtils;

public class AscendingSeperationTechniquePractice
{
    // Let's say that we have an array of numbers and we are given a value N
    // Let's separate the array into 2 parts, 1st part will be the smaller elements compared to the given value N
    // 2nd part will be larger or equal to the given value N
    // Do this inplace
    // Example: 5, 16, 7, 32, 12, 9, 10. Given N = 10. Expected array output will be: 5, 7, 9, 32, 12, 16, 10
    // Notice that the first 3 elements of the array will be < N and the rest is >= N

    private static int[] separate(int[] arr, int N)
    {
        // Practice your technique here...

        return arr;
    }

    public static void main(String[] args)
    {
        int N;
        int[] arr;

        N = 10;
        arr = new int[] { 5, 16, 7, 32, 12, 9, 10 };
        System.out.printf("\nPivot %d separation: %s\n", N, ArrayUtils.toString(separate(arr, N)));

        N = 10;
        arr = new int[] { };
        System.out.printf("\nPivot %d separation: %s\n", N, ArrayUtils.toString(separate(arr, N)));

        N = 10;
        arr = new int[] { 9 };
        System.out.printf("\nPivot %d separation: %s\n", N, ArrayUtils.toString(separate(arr, N)));

        N = 10;
        arr = new int[] { 11 };
        System.out.printf("\nPivot %d separation: %s\n", N, ArrayUtils.toString(separate(arr, N)));

        N = 10;
        arr = new int[] { 15, 19, 24, 11, 23 };
        System.out.printf("\nPivot %d separation: %s\n", N, ArrayUtils.toString(separate(arr, N)));

        N = 10;
        arr = new int[] { 9, 2, 7, -10, -22 };
        System.out.printf("\nPivot %d separation: %s\n", N, ArrayUtils.toString(separate(arr, N)));

        System.out.printf("\n");
    }
}
