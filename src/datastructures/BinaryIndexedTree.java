package datastructures;

/**
 * This is a simple implementation of Binary Indexed Tree
 */
public class BinaryIndexedTree {
    private static void updateBIT(int[] bit, int i, int value) {
        while (i < bit.length) {
            bit[i] += value;
            i += i & -i;
        }
    }

    private static int getSum(int[] bit, int i) {
        int result = 0;
        while (i > 0) {
            result += bit[i];
            i -= i & -i;
        }
        return result;
    }

    private static int rangeQuerySum(int[] bit, int i, int j) {
        int result = getSum(bit, j + 1) - getSum(bit, i);
        return j < i ? -result : result;
    }

    private static int[] constructBIT(int[] arr) {
        int[] bit = new int[arr.length + 1];
        for (int i = 0; i < arr.length; ++i) {
            updateBIT(bit, i + 1, arr[i]);
        }
        return bit;
    }

    public static void main(String[] args) {
        int[] arr = {2, -1, 3, 7, 2, 9, 18, -6, 5};
        int[] bit = constructBIT(arr);
        System.out.println(rangeQuerySum(bit, 0, 5));
        System.out.println(rangeQuerySum(bit, 3, 7));
    }
}
