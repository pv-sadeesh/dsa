package sorting;

import java.util.Arrays;

public class InsertionSort {

    /**********************************************************************************************************************************
     * Best Case    : Time Complexity: O(n log n)   Space Complexity: O(1)
     * Average Case : Time Complexity: O(n^2)       Space Complexity: O(1)
     * Worst Case   : Time Complexity: O(n^2)       Space Complexity: O(1)
     * ********************************************************************************************************************************
     * 1. Initialize Array Size:
     *      If the array has 1 or fewer elements, it's already sorted; no action is needed.
     * 2. Iterate Over Elements:
     *      Start from index 1 (as a single item is always sorted).
     *      For each element at index i, store it as key.
     * 3. Find Insert Position:
     *      Use a helper function (findPosition) to perform a binary search from the beginning of the array to i - 1.
     *      This returns the position where key should be inserted, maintaining sorted order.
     * 4. Shift Elements:
     *      If the position found is different from i, shift all elements from position to i-1 one place to the right to make space for key.
     * 5. Insert Key:
     *      Place key at the identified position.
     * 6. Binary Search Helper (findPosition):
     *      1. Perform a binary search between low and high:
     *          Move low right if arr[mid] <= key (search the right half).
     *          Move high left if arr[mid] > key (search the left half).
     *      2. Return low as the position to insert key.
     *********************************************************************************************************************************/

    static void sort(int[] arr) {
        int size = arr.length;

        // If the array size is less than or equal to 1, then the array is already sorted.
        if(size <= 1) {
            return;
        }

        // A single item in an array is always sorted by itself. Therefore, we can start the loop from index 1.
        for(int i = 1; i < size; i++) {
            int key = arr[i];
            // Find the position to insert key using binary search
            int position = findPosition(arr, key, 0, i - 1);

            if(position != i) {
                // Shift elements to the right to make space for the key
                for(int j = i; j > position; j--) {
                    arr[j] = arr[j - 1];
                }

                // Insert the key at the correct position
                arr[position] = key;
            }
        }
    }

    private static int findPosition(int[] arr, int key, int low, int high) {
        while(low <= high) {
            int mid = low + (high - low) / 2;

            // Move the low pointer to right if key is greater
            if(arr[mid] <= key) {
                low = mid + 1;
            }
            // Move the high pointer to left if key is smaller
            else {
                high = mid - 1;
            }
        }

        return low;
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 10, 4, 2, 12, 6, 9, 11, 20, 1 };
        InsertionSort.sort(arr);
        System.out.print(Arrays.toString(arr));
    }
}
