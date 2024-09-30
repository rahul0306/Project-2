package Project;

import java.util.*;

public class Project2 {
    // Quick Select, Deterministic (median of medians)

    private static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Sort elements using Insertion sort
    private static void insertionSort(int arr[], int low, int high) {
        for (int i = low; i <= high; i++) {
            int current = arr[i];
            int j = i - 1;
            while (j >= low && current < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = current;
        }
    }

    private static int medianOfMedian(int arr[], int low, int high) {
        int size = high - low + 1;
        if (size < 1) {
            return -1;
        }
        if (size <= 5) {
            Arrays.sort(arr);
            return low + size / 2;
        }

        for (int i = 0; i <= size / 5; i++) {
            int si = low + i * 5;
            int ei = Math.min(si + 4, high);
            Arrays.sort(arr, si, ei + 1);
        }

        int medians[] = new int[(size + 4) / 5];
        for (int i = 0; i < medians.length; i++) {
            int si = low + i * 5;
            int ei = Math.min(si + 4, high);
            insertionSort(arr, si, ei);
            medians[i] = arr[si + (ei - si) / 2];
        }
        return quickSelect(medians, 0, medians.length - 1, medians.length / 2);
    }

    private static int partition(int arr[], int low, int high, int pivot) {
        int pivotValue = arr[pivot];
        swap(arr, pivot, high);
        int storedIndex = low;

        for (int i = low; i < high; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, i, storedIndex);
                storedIndex++;
            }
        }
        swap(arr, storedIndex, high);
        return storedIndex;
    }

    private static int quickSelect(int arr[], int low, int high, int k) {
        if (low == high) {
            return arr[low];
        }

        int pivot = medianOfMedian(arr, low, high);
        if (pivot < low || pivot > high) {
            pivot = (low + high) / 2;
            // throw new RuntimeException("Pivot index out of bounds");
        }
        pivot = partition(arr, low, high, pivot);
        // System.out.println("Pivot: " + arr[pivot] + " at index: " + pivot);

        int leftNumber = pivot - low + 1;
        if (k == leftNumber) {
            return arr[pivot];
        } else if (k <= leftNumber) {
            return quickSelect(arr, low, pivot - 1, k);
        } else {
            return quickSelect(arr, pivot + 1, high, k - leftNumber);
        }
    }

    public static int findKth(int arr[], int k) {
        if (arr == null) {
            throw new NullPointerException("Array is null");
        }
        if (k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("K is out of bounds");
        }
        return quickSelect(arr, 0, arr.length - 1, k);
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int arr[] = new int[4000];
        HashSet<Integer> set = new HashSet<>();
        while (set.size() < 4000) {
            set.add(rand.nextInt(10000) + 1);
        }
        // for(int i=0;i<20;i++){
        // arr[i] = rand.nextInt(100)+1;
        // }
        int i = 0;
        for (Integer num : set) {
            arr[i++] = num;
        }

        for(i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
        
        // int arr[] = { 7, 10, 11, 9, 17, 1, 4, 18, 45, 93, 63, 22, 5, 99, 30, 20, 40,
        // 35, 15, 55 };
        int k = 6;
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        long startTime = System.nanoTime();
        System.out.println("The " + k + "th smallest element is: " + findKth(arr, k));
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Execution time " + duration + " ns ");
    }
}
