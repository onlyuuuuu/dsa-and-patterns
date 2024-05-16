package com.onlyu.random;

import java.util.PriorityQueue;
import java.util.Stack;

public class FindMedianInTheDataStream
{
    // Write a class to find the median in the data stream
    // Assuming all items are integers
    interface MedianExtractorDataStream
    {
        void offer(int value);
        int size();
        int getMedian();
    }

    // 1st way I can think of, using heap
    static class HeapBackedDataStream implements MedianExtractorDataStream
    {
        private PriorityQueue<Integer> heap = new PriorityQueue<>();

        @Override
        public synchronized void offer(int value)
        {
            heap.offer(value);
        }

        @Override
        public int size()
        {
            return heap.size();
        }

        @Override
        public synchronized int getMedian()
        {
            if (heap.size() == 0)
                return 0;
            if (heap.size() == 1)
                return heap.peek();
            Stack<Integer> temp = new Stack<>();
            while (temp.size() < heap.size())
                temp.push(heap.poll());
            int median = temp.size() == heap.size()
                ? (int)Math.ceil((double)(temp.peek() + heap.peek())/2)
                : temp.peek();
            while (!temp.isEmpty())
                heap.offer(temp.pop());
            return median;
        }
    }

    public static void main(String[] args)
    {
        MedianExtractorDataStream medianExtractorDataStream = new HeapBackedDataStream();
        medianExtractorDataStream.offer(1);
        medianExtractorDataStream.offer(2);
        medianExtractorDataStream.offer(3);
        medianExtractorDataStream.offer(4);
        medianExtractorDataStream.offer(5);
        medianExtractorDataStream.offer(6);
        System.out.printf("\nMedian of 1, 2, 3, 4, 5, 6       is [%d]\n", medianExtractorDataStream.getMedian());
        medianExtractorDataStream.offer(7);
        System.out.printf("\nMedian of 1, 2, 3, 4, 5, 6, 7    is [%d]\n", medianExtractorDataStream.getMedian());
        medianExtractorDataStream.offer(8);
        System.out.printf("\nMedian of 1, 2, 3, 4, 5, 6, 7, 8 is [%d]\n", medianExtractorDataStream.getMedian());
        System.out.printf("\n");
    }

}
