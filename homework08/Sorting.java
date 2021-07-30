import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Nandha Sundaravadivel
 * @version 1.0
 * @userid nsundara3
 * @GTID 903599469
 *
 *       Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 *       Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Private helper method to swap two elements in an array
     * 
     * @param <T>    The type of object in the array
     * @param index1 An index in the array
     * @param index2 Another index in the array
     * @param arr    The array to swap element in
     */
    private static <T> void swap(int index1, int index2, T[] arr) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

    /**
     * Implement selection sort.
     *
     * It should be: in-place unstable not adaptive
     *
     * Have a worst case running time of: O(n^2)
     *
     * And a best case running time of: O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (comparator == null || arr == null) {
            throw new IllegalArgumentException("The data array or the comparator can not be mull");
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int maxIndex = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }
            swap(i, maxIndex, arr);
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be: in-place stable adaptive
     *
     * Have a worst case running time of: O(n^2)
     *
     * And a best case running time of: O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You MUST
     * implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (comparator == null || arr == null) {
            throw new IllegalArgumentException("The data array or the comparator can not be mull");
        }
        boolean swapsMade = true;
        int startIndex = 0;
        int endIndex = arr.length - 1;
        while (swapsMade) {
            swapsMade = false;
            int j = endIndex;
            for (int i = startIndex; i < j; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(i, i + 1, arr);
                    swapsMade = true;
                    endIndex = i;
                }
            }
            if (swapsMade) {
                swapsMade = false;
                j = startIndex;
                for (int i = endIndex; i > j; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        swap(i, i - 1, arr);
                        startIndex = i;
                        swapsMade = true;
                    }
                }
            }
        }

    }

    /**
     * Implement merge sort.
     *
     * It should be: out-of-place stable not adaptive
     *
     * Have a worst case running time of: O(n log n)
     *
     * And a best case running time of: O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray you
     * should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator object can not be null");
        }
        merge(arr, comparator);
    }

    /**
     * Private helper method to help the merge method to recurse
     * 
     * @param <T>        The type of object that is being sorted
     * @param arr        The array that you are sorting
     * @param comparator The comparator object to sort the array by
     */
    private static <T> void merge(T[] arr, Comparator<T> comparator) {
        if (arr.length <= 1) {
            return;
        }
        int length = arr.length;
        int mid = length / 2;
        Object[] left = (T[]) new Object[mid];
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        Object[] right = (T[]) new Object[length - mid];
        for (int i = mid; i < arr.length; i++) {
            right[i - mid] = arr[i];
        }
        merge((T[]) left, comparator);
        merge((T[]) right, comparator);
        // Begin merging
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (comparator.compare((T) left[i], (T) right[j]) <= 0) {
                arr[i + j] = (T) left[i];
                i++;
            } else {
                arr[i + j] = (T) right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = (T) left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = (T) right[j];
            j++;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you need
     * a pivot between a (inclusive) and b (exclusive) where b > a, use the
     * following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one, the
     * formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be: in-place unstable not adaptive
     *
     * Have a worst case running time of: O(n^2)
     *
     * And a best case running time of: O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class. There
     * are several versions of this algorithm and you may not receive credit if you
     * do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or rand
     *                                            is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("The array or comparator object can not be null");
        }
        quickSort(arr, 0, arr.length - 1, rand, comparator);
    }

    /**
     * A private helper method to help quicksort recurse through the array
     * 
     * @param <T>        The type of object contained in the array
     * @param arr        The array being sorted
     * @param start      The start index of the array being sorter
     * @param end        The end index of the array being sorted inclusive
     * @param rand       The random object used to select the pivot point
     * @param comparator The comparator object to sort the array by
     */
    private static <T> void quickSort(T[] arr, int start, int end, Random rand, Comparator<T> comparator) {
        if (end - start < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        swap(pivotIndex, start, arr);
        pivotIndex = start;
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], arr[pivotIndex]) <= 0) {
                i++;
            }
            while (i < j && comparator.compare(arr[j], arr[pivotIndex]) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(i, j, arr);
                i++;
                j--;
            }
        }
        swap(start, j, arr);
        quickSort(arr, start, j - 1, rand, comparator);
        quickSort(arr, j + 1, end, rand, comparator);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class. There
     * are several versions of this algorithm and you may not get full credit if you
     * do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your code!
     * Doing so may result in a 0 for the implementation.
     *
     * It should be: out-of-place stable not adaptive
     *
     * Have a worst case running time of: O(kn)
     *
     * And a best case running time of: O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to determine
     * the number of iterations you need. The number of iterations can be determined
     * using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a number;
     * any such method would be non-O(1). Think about how how you can get each power
     * of BASE naturally and efficiently as the algorithm progresses through each
     * digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be used
     * inside radix sort and any radix sort helpers. Do NOT use these classes with
     * other sorts. However, be sure the List implementation you choose allows for
     * stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array that you are passing in can not be null");
        }
        int digits = findMaxDigits(arr);
        ArrayList<Integer>[] buckets = new ArrayList[19];
        for (int j = 0; j < digits; j++) {
            for (int i = 0; i < 19; i++) {
                buckets[i] = new ArrayList<>();
            }
            for (int i = 0; i < arr.length; i++) {
                buckets[calculateIndex(arr[i], j)].add(arr[i]);
            }
            int index = 0;
            for (ArrayList<Integer> bucket : buckets) {
                for (Integer h : bucket) {
                    if (h != null) {
                        arr[index] = h;
                        index++;
                    }
                }
            }

        }
    }

    /**
     * Private helper method to determine which index bucket to place an object in
     * 
     * @param num   The number that needs to be placed in a bucket
     * @param digit The digit of the number which needs to be selected
     * @return The index of bucket that the number will need to be placed in
     */
    private static int calculateIndex(int num, int digit) {
        int returnNum = Math.abs(num);
        boolean negative = false;
        if (num < 0) {
            negative = true;
        }
        while (digit > 0) {
            returnNum /= 10;
            digit--;
        }
        returnNum %= 10;
        if (negative) {
            returnNum *= -1;
        }

        return returnNum + 9;
    }

    /**
     * Private helper method to determine the number with the most number of digits
     * in an array
     * 
     * @param arr The array to search
     * @return The number of digits in the number with the greatests magnitude in
     *         the array
     */
    private static int findMaxDigits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length - 1; i++) {
            max = Integer.max(max, Math.abs(arr[i]));
        }
        int digits = 0;
        while (max != 0) {
            max = max / 10;
            digits++;
        }
        return digits;
    }

    /**
     * Implement heap sort.
     *
     * It should be: out-of-place unstable not adaptive
     *
     * Have a worst case running time of: O(n log n)
     *
     * And a best case running time of: O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this PriorityQueue
     * implementation, elements are removed from smallest element to largest
     * element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at the
     * different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that holds
     *         the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are passing in can not be null");
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>(data);
        int index = 0;
        int[] arr = new int[data.size()];
        while (!pq.isEmpty()) {
            arr[index] = pq.remove();
            index++;
        }
        return arr;
    }
}
