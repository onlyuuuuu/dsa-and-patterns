package com.onlyu.random;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeSet;

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
    // I think this is (2*N.logN)
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

    // 2nd way, may be using binary tree is better
    //
    static class BinaryTreeBackedDataStream implements MedianExtractorDataStream
    {
        private TreeSet<Integer> tree = new TreeSet<>();

        @Override
        public void offer(int value)
        {
            tree.add(value);
        }

        @Override
        public int size()
        {
            return tree.size();
        }

        @Override
        public int getMedian()
        {
            if (tree.size() == 0)
                return 0;
            if (tree.size() == 1)
                return tree.first();
            int traversed = -1;
            int half = (tree.size() - 1)/2;
            int median = 0;
            Iterator<Integer> iterator = tree.iterator();
            while (traversed != half)
            {
                median = iterator.next();
                traversed++;
            }
            if (traversed + 1 != tree.size() - (traversed + 1))
                return median;
            return (int)Math.ceil((double)(median + iterator.next())/2);
        }
    }

    public static void main(String[] args)
    {
        MedianExtractorDataStream heapBacked = new HeapBackedDataStream();
        heapBacked.offer(1);
        heapBacked.offer(2);
        heapBacked.offer(3);
        heapBacked.offer(4);
        heapBacked.offer(5);
        heapBacked.offer(6);

        MedianExtractorDataStream treeBacked = new HeapBackedDataStream();
        treeBacked.offer(1);
        treeBacked.offer(2);
        treeBacked.offer(3);
        treeBacked.offer(4);
        treeBacked.offer(5);
        treeBacked.offer(6);

        System.out.printf("\n[Heap Implementation] Median of 1, 2, 3, 4, 5, 6       is [%d]\n", heapBacked.getMedian());
        System.out.printf("\n[Tree Implementation] Median of 1, 2, 3, 4, 5, 6       is [%d]\n", treeBacked.getMedian());

        heapBacked.offer(7);
        treeBacked.offer(7);

        System.out.printf("\n[Heap Implementation] Median of 1, 2, 3, 4, 5, 6, 7    is [%d]\n", heapBacked.getMedian());
        System.out.printf("\n[Tree Implementation] Median of 1, 2, 3, 4, 5, 6, 7    is [%d]\n", treeBacked.getMedian());

        heapBacked.offer(8);
        treeBacked.offer(8);

        System.out.printf("\n[Heap Implementation] Median of 1, 2, 3, 4, 5, 6, 7, 8 is [%d]\n", heapBacked.getMedian());
        System.out.printf("\n[Tree Implementation] Median of 1, 2, 3, 4, 5, 6, 7, 8 is [%d]\n", treeBacked.getMedian());

        System.out.printf("\n");
    }

}
