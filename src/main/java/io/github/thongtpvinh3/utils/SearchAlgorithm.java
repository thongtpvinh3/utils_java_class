package io.github.thongtpvinh3.utils;

import java.util.Arrays;

public class SearchAlgorithm {
    /*
     * Return index of target, if not found return -1
     */
    public static int linearSearch(int[] arr, int target) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }

    public static int binarySearch(int[] arr, int l, int r, int target) {
        if (r >= l) {
            int mid = l + (r - l) / 2;

            // If the element is present at the
            // middle itself
            if (arr[mid] == target) return mid;

            // If element is smaller than mid, then
            // it can only be present in left subarray
            if (arr[mid] > target) return binarySearch(arr, l, mid - 1, target);

            // Else the element can only be present
            // in right subarray
            return binarySearch(arr, mid + 1, r, target);
        }

        // We reach here when element is not present
        // in array
        return -1;
    }

    public static int ternarySearch(int startIndex, int endIndex, int key, int[] arr) {
        if (endIndex >= startIndex) {

            // Find the mid1 and mid2
            int mid1 = startIndex + (endIndex - startIndex) / 3;
            int mid2 = endIndex - (endIndex - startIndex) / 3;

            // Check if key is present at any mid
            if (arr[mid1] == key) {
                return mid1;
            }
            if (arr[mid2] == key) {
                return mid2;
            }

            // Since key is not present at mid,
            // check in which region it is present
            // then repeat the Search operation
            // in that region

            if (key < arr[mid1]) {

                // The key lies in between l and mid1
                return ternarySearch(startIndex, mid1 - 1, key, arr);
            } else if (key > arr[mid2]) {

                // The key lies in between mid2 and r
                return ternarySearch(mid2 + 1, endIndex, key, arr);
            } else {

                // The key lies in between mid1 and mid2
                return ternarySearch(mid1 + 1, mid2 - 1, key, arr);
            }
        }

        // Key not found
        return -1;
    }

    public static int jumpSearch(int[] arr, int target) {
        int n = arr.length;

        // Finding block size to be jumped
        int step = (int) Math.floor(Math.sqrt(n));

        // Finding the block where element is
        // present (if it is present)
        int prev = 0;
        for (int minStep = Math.min(step, n) - 1; arr[minStep] < target; minStep = Math.min(step, n) - 1) {
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) return -1;
        }

        // Doing a linear search for x in block
        // beginning with prev.
        while (arr[prev] < target) {
            prev++;

            // If we reached next block or end of
            // array, element is not present.
            if (prev == Math.min(step, n)) return -1;
        }

        // If element is found
        if (arr[prev] == target) return prev;

        return -1;
    }

    // If x is present in arr[0..n-1], then returns
    // index of it, else returns -1.
    public static int interpolationSearch(int[] arr, int startIndex, int endIndex, int x) {
        int pos;

        // Since array is sorted, an element
        // present in array must be in range
        // defined by corner
        if (startIndex <= endIndex && x >= arr[startIndex] && x <= arr[endIndex]) {

            // Probing the position with keeping
            // uniform distribution in mind.
            pos = startIndex + (((endIndex - startIndex) / (arr[endIndex] - arr[startIndex])) * (x - arr[startIndex]));

            // Condition of target found
            if (arr[pos] == x) return pos;

            // If x is larger, x is in right sub array
            if (arr[pos] < x) return interpolationSearch(arr, pos + 1, endIndex, x);

            // If x is smaller, x is in left sub array
            if (arr[pos] > x) return interpolationSearch(arr, startIndex, pos - 1, x);
        }
        return -1;
    }

    // Returns position of
    // first occurrence of
    // x in array
    public static int exponentialSearch(int[] arr, int length, int target) {
        // If x is present at first location itself
        if (arr[0] == target) return 0;

        // Find range for binary search by
        // repeated doubling
        int i = 1;
        while (i < length && arr[i] <= target) i = i * 2;

        // Call binary search for the found range.
        return Arrays.binarySearch(arr, i / 2, Math.min(i, length - 1), target);
    }

    /* Returns index of x if present, else returns -1 */
    public static int fibMonaccianSearch(int[] arr, int x, int n) {
        /* Initialize fibonacci numbers */
        int fibMMm2 = 0; // (m-2)'th Fibonacci No.
        int fibMMm1 = 1; // (m-1)'th Fibonacci No.
        int fibM = fibMMm2 + fibMMm1; // m'th Fibonacci

        /* fibM is going to store the smallest
        Fibonacci Number greater than or equal to n */
        while (fibM < n) {
            fibMMm2 = fibMMm1;
            fibMMm1 = fibM;
            fibM = fibMMm2 + fibMMm1;
        }

        // Marks the eliminated range from front
        int offset = -1;

        /* while there are elements to be inspected.
        Note that we compare arr[fibMm2] with x.
        When fibM becomes 1, fibMm2 becomes 0 */
        while (fibM > 1) {
            // Check if fibMm2 is a valid location
            int i = Math.min(offset + fibMMm2, n - 1);

            /* If x is greater than the value at
            index fibMm2, cut the subarray array
            from offset to i */
            if (arr[i] < x) {
                fibM = fibMMm1;
                fibMMm1 = fibMMm2;
                fibMMm2 = fibM - fibMMm1;
                offset = i;
            }

            /* If x is less than the value at index
            fibMm2, cut the subarray after i+1 */
            else if (arr[i] > x) {
                fibM = fibMMm2;
                fibMMm1 = fibMMm1 - fibMMm2;
                fibMMm2 = fibM - fibMMm1;
            }

            /* element found. return index */
            else return i;
        }

        /* comparing the last element with x */
        if (fibMMm1 == 1 && arr[n - 1] == x) return n - 1;

        /*element not found. return -1 */
        return -1;
    }
}
