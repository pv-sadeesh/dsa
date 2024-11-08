package sorting.optimised;

import java.util.Arrays;

public class MergeSort {

    /*********************************************************************************************************************************
     * Best Case    : Time Complexity: O(n log n)   Space Complexity: O(n) = O(n + log n)
     * Average Case : Time Complexity: O(n log n)   Space Complexity: O(n) = O(n + log n)
     * Worst Case   : Time Complexity: O(n log n)   Space Complexity: O(n) = O(n + log n)
     *********************************************************************************************************************************
     * 1. Divide: 
     *      Recursively split the array into two halves until each subarray has one element.
     * 2. Conquer: 
     *      Sort each half by recursively applying the same split process.
     * 3. Merge: 
     *      Combine (merge) the two sorted halves into a single sorted array in each recursive call.
     * 4. Combine Steps:
     *      Split the array using a midpoint.
     *      Use temporary arrays to hold each half.
     *      Merge elements back into the original array in sorted order.
     *********************************************************************************************************************************/

    static void sort(int[] arr) {
        int size = arr.length;
        // No sorting needed for an array with size 0 or 1
        if (size <= 1) {
            return;
        }

        // Initiate recursive sorting on the full array; Time Complexity: O(n log n)
        sort(arr, 0, size - 1); // O(log n)
    }

    private static void sort(int[] arr, int low, int high) {
        if (low < high) {
            // Calculate midpoint to divide array
            int mid = low + (high - low) / 2;

            // Recursively sort the left half; Time Complexity per call: O(log n)
            sort(arr, low, mid);
            // Recursively sort the right half; Time Complexity per call: O(log n)
            sort(arr, mid + 1, high);

            // Merge the sorted halves; Time Complexity per call: O(n)
            merge(arr, low, mid, high);
        }
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        int firstArraySize = mid - low + 1;
        int secondArraySize = high - mid;

        // Create temporary arrays for left and right subarrays
        int[] firstArray = new int[firstArraySize];
        int[] secondArray = new int[secondArraySize];

        // Copy data into the left subarray
        for (int i = 0; i < firstArraySize; i++) {
            firstArray[i] = arr[low + i];
        }

        // Copy data into the right subarray
        for (int i = 0; i < secondArraySize; i++) {
            secondArray[i] = arr[mid + 1 + i];
        }

        int i = 0, j = 0, k = low;

        // Merge elements from both arrays in sorted order until one array is exhausted
        // Merge process within a single call: O(n)
        while (i < firstArraySize && j < secondArraySize) { // O(n)
            if (firstArray[i] < secondArray[j]) {
                arr[k] = firstArray[i];
                i++;
            } else {
                arr[k] = secondArray[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements from the left subarray, if any
        while (i < firstArraySize) {
            arr[k] = firstArray[i];
            i++;
            k++;
        }

        // Copy remaining elements from the right subarray, if any
        while (j < secondArraySize) {
            arr[k] = secondArray[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 10, 4, 2, 12, 6, 9, 11, 20, 1 };
        MergeSort.sort(arr);
        System.out.print(Arrays.toString(arr));
    }
}
