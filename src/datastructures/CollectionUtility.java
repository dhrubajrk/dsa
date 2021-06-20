package datastructures;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionUtility {
    private void sortInDescendingOrder(final List<Integer> nums) {
        nums.sort((o1, o2) -> o2 - o1);
        System.out.println(nums.stream().map(String::valueOf).collect(Collectors.joining(", ")));
    }

    private void findMin(final List<Integer> nums) {
//        System.out.println(Collections.max(nums, (o1, o2) -> o2 - o1)); // USING COMPARATOR
        System.out.println(Collections.min(nums));
    }

    public static void main(String[] args) {
        final CollectionUtility collectionUtility = new CollectionUtility();

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        collectionUtility.sortInDescendingOrder(nums);

        collectionUtility.findMin(nums);
    }
}
