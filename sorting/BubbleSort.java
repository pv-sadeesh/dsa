package sorting;

import java.util.Arrays;

public class BubbleSort {
    
    // Best Case    : Time Complexity: O(n)    Space Complexity: O(1)
    // Average Case : Time Complexity: O(n^2)  Space Complexity: O(1)
    // Worst Case   : Time Complexity: O(n^2)  Space Complexity: O(1)

    // 1. Check Array Size: If the array has 0 or 1 element, it's already sorted; return.
    // 2. Outer Loop: Loop from i = 0 to size - 2.
    //      Initialize swapped = false.
    // 3. Inner Loop: For each element j from 0 to size - 2 - i:
    //      Compare arr[j] and arr[j + 1]. If arr[j] > arr[j + 1], swap and set swapped = true.
    // 4. Early Exit: If no swaps occurred in a pass (swapped = false), the array is sorted; return.
    // 5. Repeat Until Sorted: Continue passes until the array is fully sorted.

    static void sort(int[] arr) {
        int size = arr.length;

        // If the array size is less than or equal to 1,
        // then the array is already sorted.
        if(size <= 1) {
            return;
        }

        // Since the last iteration sorts the first two items, 
        // we only need to iterate up to size - 1.
        for(int i = 0; i < size - 1; i++) {
            boolean swapped = false;

            // With each iteration, items on the right side are sorted one by one.
            // Therefore, we can limit the inner loop to j < size - 1 - i.
            for(int j = 0; j < size - 1 - i; j++) {
                if(arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }

            // If no items were swapped in this iteration, 
            // the array is already sorted, so we can return early.
            if(!swapped) {
                return;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{10,4,2,12,6,9,11,20,1};
        BubbleSort.sort(arr);
        System.out.print(Arrays.toString(arr));
    }
}
