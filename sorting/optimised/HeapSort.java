package sorting.optimised;

import java.util.Arrays;

public class HeapSort {

    /*********************************************************************************************************************************
     * Best Case    : Time Complexity: O(n log n)   Space Complexity: O(1)
     * Average Case : Time Complexity: O(n log n)   Space Complexity: O(1)
     * Worst Case   : Time Complexity: O(n log n)   Space Complexity: O(1)
     *********************************************************************************************************************************
     * 1. Build Max Heap:
     *      Start from the last non-leaf node and apply heapify up to the root to build a max heap.
     * 2. Sort in Place:
     *      Swap the root (largest element) with the last element.
     *      Reduce the heap size by 1 and apply heapify to restore the max heap property.
     *      Repeat until the array is sorted.
     *********************************************************************************************************************************/

    static void sort(int[] arr) {
        int size = arr.length;

        // If the array has one or no elements, it is already sorted
        if (size <= 1) {
            return;
        }

        // Step 1: Build the max heap
        // Start from the last non-leaf node and apply heapify in reverse level order
        for (int i = (size / 2) - 1; i >= 0; i--) { // O(n)
            heapify(arr, size, i);
        }

        // Step 2: Extract elements one by one from the heap
        for (int i = size - 1; i > 0; i--) { // O(n)
            // Move the current root (largest element) to the end
            swap(arr, i, 0);
            // Call heapify on the reduced heap to restore max-heap property
            heapify(arr, i, 0); // O(log n)
        }
    }

    private static void heapify(int[] arr, int size, int index) {
        while(true) {
            int largestIndex = index;
            int leftChildIndex = (index * 2) + 1;
            int rightChildIndex = (index * 2) + 2;

            // If left child is larger than root
            if (leftChildIndex < size && arr[leftChildIndex] > arr[largestIndex]) {
                largestIndex = leftChildIndex;
            }

            // If right child is larger than largest so far
            if (rightChildIndex < size && arr[rightChildIndex] > arr[largestIndex]) {
                largestIndex = rightChildIndex;
            }

            // If root and largest are the same, the heap property is satisfied
            if (largestIndex == index) {
                break;
            }

            // Swap root with the largest element
            swap(arr, largestIndex, index);
            // Recursively heapify the affected subtree
            index = largestIndex;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 10, 4, 2, 12, 6, 9, 11, 20, 1 };
        HeapSort.sort(arr);
        System.out.print(Arrays.toString(arr));
    }
}
