package com.onlyu.random;

import java.util.*;

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
    // I think this is (logN) or rather (N*logN) which is a little bit better
    // We might come up with something better next time
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

    // 3rd way, even better, taken from the idea of AVL tree, which is the storing the height of a left subtree and right subtree
    // Adding costs O(logN), maybe O(2logN) and getting costs O(1)
    static class DoubleHeapBackedDataStream implements MedianExtractorDataStream
    {
        private PriorityQueue<Integer> lesserLeftMaxHeap = new PriorityQueue<>(Collections.reverseOrder());
        private PriorityQueue<Integer> largerRightMinHeap = new PriorityQueue<>();
        private Integer median = null;

        @Override
        public void offer(int value)
        {
            if (median == null)
            {
                median = value;
                return;
            }
            if (value < median)
                lesserLeftMaxHeap.offer(value);
            else largerRightMinHeap.offer(value);
            int offset = lesserLeftMaxHeap.size() - largerRightMinHeap.size();
            if (offset == 2)
            {
                largerRightMinHeap.offer(median);
                median = lesserLeftMaxHeap.poll();
            }
            else if (offset == -2)
            {
                lesserLeftMaxHeap.offer(median);
                median = largerRightMinHeap.poll();
            }
        }

        @Override
        public int size()
        {
            return lesserLeftMaxHeap.size() + largerRightMinHeap.size() + 1;
        }

        @Override
        public int getMedian()
        {
            int offset = lesserLeftMaxHeap.size() - largerRightMinHeap.size();
            if (offset == 1)
                return (int)Math.ceil((double)(lesserLeftMaxHeap.peek() + median)/2);
            else if (offset == -1)
                return (int)Math.ceil((double)(largerRightMinHeap.peek() + median)/2);
            else if (median == null)
                return 0;
            else
                return median;
        }
    }

    public static void main(String[] args)
    {
        List<MedianExtractorDataStream> dataStreams = Arrays.asList
        (
            new HeapBackedDataStream(),
            new BinaryTreeBackedDataStream(),
            new DoubleHeapBackedDataStream()
        );

        MedianExtractorDataStream heapBacked = dataStreams.get(0);
        MedianExtractorDataStream treeBacked = dataStreams.get(1);
        MedianExtractorDataStream doubleHeapBacked = dataStreams.get(2);

        for (MedianExtractorDataStream stream : dataStreams)
        {
            stream.offer(1);
            stream.offer(2);
            stream.offer(3);
            stream.offer(4);
            stream.offer(5);
            stream.offer(6);
        }

        System.out.printf("\n[Heap Implementation] Median of 1, 2, 3, 4, 5, 6          is [%d]\n", heapBacked.getMedian());
        System.out.printf("\n[Tree Implementation] Median of 1, 2, 3, 4, 5, 6          is [%d]\n", treeBacked.getMedian());
        System.out.printf("\n[Heap Implementation x2] Median of 1, 2, 3, 4, 5, 6       is [%d]\n", doubleHeapBacked.getMedian());

        for (MedianExtractorDataStream stream : dataStreams)
            stream.offer(7);

        System.out.printf("\n[Heap Implementation] Median of 1, 2, 3, 4, 5, 6, 7       is [%d]\n", heapBacked.getMedian());
        System.out.printf("\n[Tree Implementation] Median of 1, 2, 3, 4, 5, 6, 7       is [%d]\n", treeBacked.getMedian());
        System.out.printf("\n[Tree Implementation x2] Median of 1, 2, 3, 4, 5, 6, 7    is [%d]\n", doubleHeapBacked.getMedian());

        for (MedianExtractorDataStream stream : dataStreams)
            stream.offer(8);

        System.out.printf("\n[Heap Implementation] Median of 1, 2, 3, 4, 5, 6, 7, 8    is [%d]\n", heapBacked.getMedian());
        System.out.printf("\n[Tree Implementation] Median of 1, 2, 3, 4, 5, 6, 7, 8    is [%d]\n", treeBacked.getMedian());
        System.out.printf("\n[Tree Implementation x2] Median of 1, 2, 3, 4, 5, 6, 7, 8 is [%d]\n", doubleHeapBacked.getMedian());

        System.out.printf("\n");
    }

}
