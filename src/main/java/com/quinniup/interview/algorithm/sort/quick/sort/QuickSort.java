package com.quinniup.interview.algorithm.sort.quick.sort;

import java.util.Arrays;

/**
 * @ClassName: QuickSort
 * @Description: 快排思想：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比比另外一部分的所有数据小
 * 然后按照此方式对两部分数据分别进行快速排序，整个过程通过递归方式进行，直到所有数据有序。
 * @Author: CheneyIn
 * @Date: 2021/1/11
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] data = new int[]{13, 42, 55, 35, 98, 64, 19, 30, 11};
        quicksort(data);
    }

    private static void quicksort(int[] data) {
        sort(data, 0, data.length - 1);
    }

    private static void sort(int[] data, int start, int end) {
        if (start < end) {
            int index = partition(data, start, end);

            sort(data, start, index - 1);
            sort(data, index + 1, end);
        }
        System.out.println(Arrays.toString(data));
    }

    private static int partition(int[] data, int start, int end) {
        int stand = data[start];
        int i = start;
        int j = end;
        while (i < j) {
            while (data[j] >= stand && i < j) {
                j--;
            }
            while (data[i] <= stand && i < j) {
                i++;
            }
            swap(data, i, j);
        }
        swap(data, start, i);
        return i;
    }

    private static void swap(int[] data, int start, int end) {
        int temp = data[start];
        data[start] = data[end];
        data[end] = temp;
    }
}
