package sorting;

import java.util.Arrays;

public class SelectionSort {

    /*********************************************************************************************************************************
     * Best Case    : Time Complexity: O(n^2)   Space Complexity: O(1)
     * Average Case : Time Complexity: O(n^2)   Space Complexity: O(1)
     * Worst Case   : Time Complexity: O(n^2)   Space Complexity: O(1)
     ********************************************************************************************************************************* 
     * 1. Check Array Size: 
     *      If the array has 1 or fewer elements, it's already sorted (return immediately).
     * 2. Outer Loop:
     *      Iterate from the 0th element to the second last element (i = 0 to size-1).
     * 3. Find Minimum: 
     *      For each element i, iterate through the rest of the array (j = i+1 to size) to find the index of the smallest element.
     * 4. Swap: 
     *      If the smallest element found is not already at the ith position, swap it with the element at the ith position.
     * 5. Repeat:
     *      Continue the process until all elements are sorted.
     *********************************************************************************************************************************/

    static void sort(int[] arr) {
        int size = arr.length;

        // If the array size is less than or equal to 1, then the array is already sorted.
        if (size <= 1) {
            return;
        }

        // Once the first n-1 elements are sorted, the "n"th element will be automatically sorted.
        // Therefore, we only need to run size-1 iterations.
        for (int i = 0; i < size - 1; i++) {
            int mindIndex = i;

            // Compare the "i"th element with all elements after it to find the index of the smallest element.
            for (int j = i + 1; j < size; j++) {
                if (arr[j] < arr[mindIndex]) {
                    mindIndex = j;
                }
            }

            // If the minimum element is not at the "i"th position, swap it with the element at the "i"th position.
            if (mindIndex != i) {
                swap(arr, i, mindIndex);
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 10, 4, 2, 12, 6, 9, 11, 20, 1 };
        SelectionSort.sort(arr);
        System.out.print(Arrays.toString(arr));
    }
}
