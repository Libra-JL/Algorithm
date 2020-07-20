package com.lijie.chapter02;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectSort {


    private static List<Integer> selectionSort(List<Integer> arr) {
        List<Integer> newArr = new ArrayList<>(arr.size());

        int size = arr.size();
        for (int i = 0; i < size; i++) {
            int smallest = findSmallIndex(arr);
            newArr.add(arr.get(smallest));

            arr.remove(smallest);
        }

        return newArr;
    }


    private static int findSmallIndex(List<Integer> arr) {
        int smallest = arr.get(0);
        int smallist_index = 0;

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) < smallest) {
                smallest = arr.get(i);
                smallist_index = i;
            }
        }

        return smallist_index;
    }


    public static void main(String[] args) {

        List<Integer> arr = new ArrayList<>(Arrays.asList(3, 2, 4, 6, 5));
        System.out.println(selectionSort(arr));
    }


}
