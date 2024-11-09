package sorting;

import java.util.Arrays;
import java.lang.System;

public class RadixSort {

    /*********************************************************************************************************************************
     * Best Case    : Time Complexity: O(d * (n + k))   Space Complexity: O(n + k)
     * Average Case : Time Complexity: O(d * (n + k))   Space Complexity: O(n + k)
     * Worst Case   : Time Complexity: O(d * (n + k))   Space Complexity: O(n + k) 
     *********************************************************************************************************************************
     * 1. Find Maximum Element: 
     *      Identify the maximum number in the array to determine the number of digits.
     * 2. Counting Sort by Digit:
     *      Sort the array based on each digit (starting from the least significant digit).
     * 3. Use counting sort for each digit.
     *      Repeat for Each Digit: Continue sorting by each digit (place value: 1, 10, 100, etc.) until all digits are processed.
     * 4. Stability: 
     *      Radix Sort is stable, preserving the order of equal elements from previous passes.
     *********************************************************************************************************************************/

    static void sort(int[] arr) {
        int size = arr.length;

        // If the array size is less than or equal to 1, then the array is already sorted.
        if (size <= 1) {
            return;
        }

        // Find the maximum number to determine the number of digits
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) { // O(n)
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // Perform counting sort for each digit (place), starting from the least significant digit
        for (int exp = 1; max / exp > 0; exp *= 10) { // O(d)
            countingSortByDigit(arr, exp); // O(n + k)
        }
    }

    private static void countingSortByDigit(int[] arr, int exp) {
        int size = arr.length;
        int[] countArray = new int[10];

        // Count occurrences of each digit at the current place (exp)
        for (int item : arr) { // O(n)
            int digit = (item / exp) % 10;
            countArray[digit]++;
        }

        // Update the count array to contain positions of digits
        for (int i = 1; i < countArray.length; i++) { // O(k)
            countArray[i] += countArray[i - 1];
        }

        // Build the output array by placing each element at its correct position
        int[] output = new int[size];
        for (int i = size - 1; i >= 0; i--) { // O(n)
            int digit = (arr[i] / exp) % 10;
            int index = --countArray[digit];
            output[index] = arr[i];
        }

        // Copy the sorted output back to the input array
        System.arraycopy(output, 0, arr, 0, size);
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 10, 4, 2, 12, 6, 9, 11, 20, 1 };
        RadixSort.sort(arr);
        System.out.print(Arrays.toString(arr));
    }
}
