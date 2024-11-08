package sorting;

import java.util.Arrays;

public class CountingSort {

    /*********************************************************************************************************************************
     * Best Case    : Time Complexity: O(n + k)   Space Complexity: O(n + k)
     * Average Case : Time Complexity: O(n + k)   Space Complexity: O(n + k)
     * Worst Case   : Time Complexity: O(n + k)   Space Complexity: O(n + k)
     *********************************************************************************************************************************
     * Find Range:
     *      Identify the minimum and maximum values in the input array.
     * Initialize Count Array:
     *      Create a count array with size equal to the range of input values (max - min + 1).
     *      Initialize all values of the count array to 0.
     * Count Frequencies:
     *      Traverse the input array and for each element, increment its corresponding index in the count array (count[value - min]).
     * Update Count Array:
     *      Modify the count array to store cumulative counts, i.e., each element at index i will contain the total count of elements less than or equal to i.
     * Place Elements in Output:
     *      Create an output array of the same size as the input array.
     *      Traverse the input array in reverse order to maintain stable sorting.
     *      For each element, find its correct position in the output array using the count array, then decrement the count for that element.
     * Return Sorted Array:
     *********************************************************************************************************************************/

    private static class Aggregation {
        int min;
        int max;
    }

    static int[] sort(int[] arr) {
        int size = arr.length;

        // If the array size is less than or equal to 1, then the array is already sorted.
        if (size <= 1) {
            return arr;
        }

        Aggregation aggregation = getAggregation(arr); // O(n)
        int[] countArray = getCountArray(arr, aggregation); // O(n + k)
        int[] output = new int[size];

        // Populate the output array based on the countArray.
        for (int i = size - 1; i >= 0; i--) { // O(n)
            int value = arr[i];
            int countArrayIndex = value - aggregation.min;

            countArray[countArrayIndex]--;
            int correctPosition = countArray[countArrayIndex];
            output[correctPosition] = value;
        }

        return output;
    }

    private static Aggregation getAggregation(int[] arr) {
        Aggregation aggregation = new Aggregation();

        // Find min and max to optimize the counting array size
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        aggregation.min = min;
        aggregation.max = max;

        return aggregation;
    }

    private static int[] getCountArray(int[] arr, Aggregation aggregation) {
        int countArraySize = aggregation.max - aggregation.min + 1;
        int[] countArray = new int[countArraySize];

        // Count each element
        for (int item : arr) { // O(n)
            countArray[item - aggregation.min]++;
        }

        // Update countArray to store cumulative counts/prefix count
        for (int i = 1; i < countArraySize; i++) { // O(k)
            countArray[i] += countArray[i - 1];
        }

        return countArray;
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 10, 4, 2, 12, 6, 9, 11, 20, 1 };
        arr = CountingSort.sort(arr);
        System.out.print(Arrays.toString(arr));
    }
}
