package com.lijie.chapter01;

import java.util.Arrays;

public class BinarySearch {
    /**
     * 二分查找首先需要保证目标数组有序
     * 本来需要自我实现排序算法，但是由于
     * 这里是入门，所以先使用jdk提供的排序算法来排序
     *
     * @param key 需要查找的值
     * @param arr 目标数组
     * @return 返回目标值下标或者null
     */

    public static int binarySearch(int key, int[] arr) {
        Arrays.sort(arr);
        int low = 0;
        int high = arr.length - 1;
        int mid = (low + high) / 2;
        while (low < high) {
            if (arr[mid] == key) {
                return mid;
            } else if (arr[mid] < key) {
                low = mid;
                mid = (low + high) / 2;
            } else {
                high = mid;
                mid = (low + high) / 2;
            }
        }
        return -1;
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        int[] a = {5, 4, 3, 6, 2, 1, 8, 7, 9, 0};
        int target = 1;
        int i = binarySearch(target, a);
        System.out.println(i);
    }
}
