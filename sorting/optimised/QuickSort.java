package sorting.optimised;

import java.util.Arrays;

public class QuickSort {

    /*********************************************************************************************************************************
     * Best Case    : Time Complexity: O(n log n)   Space Complexity: O(log n)
     * Average Case : Time Complexity: O(n log n)   Space Complexity: O(log n)
     * Worst Case   : Time Complexity: O(n^2)       Space Complexity: O(n)
     ********************************************************************************************************************************* 
     * 1. Choose Pivot: 
     *      Select a pivot element (typically the last element in the array or subarray).
     * 2. Partition: 
     *      Rearrange the array so elements less than the pivot are on the left, and elements greater are on the right. 
     *      Place the pivot in its correct sorted position.
     * 3. Recursive Sort:
     *      Recursively apply QuickSort to the subarray on the left of the pivot.
     *      Recursively apply QuickSort to the subarray on the right of the pivot.
     * 4. Base Case: 
     *      Stop recursion when subarray size is 0 or 1.
     *********************************************************************************************************************************/

    static void sort(int[] arr) {
        int size = arr.length;

        // If the array size is less than or equal to 1, then the array is already sorted.
        if (size <= 1) {
            return;
        }

        // Call the recursive QuickSort function on the full array
        sort(arr, 0, size - 1); // O(log n) OR O(n) for unbalnced array
    }

    private static void sort(int[] arr, int low, int high) {
        if (low < high) {
            // Adjust pivot to its correct position and get its index
            int pivotIndex = adjustPivot(arr, low, high); // O(n)

            // Recursively sort elements before the pivot
            sort(arr, low, pivotIndex - 1);
            // Recursively sort elements after the pivot
            sort(arr, pivotIndex + 1, high);
        }
    }

    private static int adjustPivot(int[] arr, int low, int high) {
        int i = low - 1;
        // Set pivot as the last element of the current subarray
        int pivot = arr[high];

        // Loop through all elements in the subarray
        for (int j = low; j < high; j++) { // O(n)
            // If current element is less than the pivot
            if (arr[j] < pivot) {
                i++; // Increment the pointer for smaller element
                swap(arr, i, j); // Swap elements to keep smaller ones on the left
            }
        }

        // Place the pivot after the last smaller element
        i++;
        swap(arr, i, high);

        return i; // Return pivot's final index
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 10, 4, 2, 12, 6, 9, 11, 20, 1 };
        QuickSort.sort(arr);
        System.out.print(Arrays.toString(arr));
    }
}
