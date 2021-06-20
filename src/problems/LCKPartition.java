package problems;

import java.util.ArrayList;
import java.util.List;

public class LCKPartition {
    public static boolean canPartitionKSubsets(int[] nums, int k) {
        if(k==1)
            return true;
        List<Integer> masks = new ArrayList<>();
        int sum = 0;
        for(int x: nums) {
            sum+=x;
        }
        if(sum%k !=0)
            return false;

        getBitMasks(nums, sum/k, 0, 0, masks);
        return findKPartition(masks, 0, 0, k, (1<<nums.length)-1);
    }

    private static boolean findKPartition(List<Integer> masks, int runningMask, int i, int k, int totalMask) {
        if(k==0) {
            return runningMask == totalMask;
        }
        if(i>=masks.size())
            return false;
        return findKPartition(masks, runningMask, i+1, k, totalMask) ||
                ((runningMask&masks.get(i)) == 0 &&
                        findKPartition(masks, runningMask|masks.get(i), i+1, k-1, totalMask));
    }


    public static void getBitMasks(int[] nums, int sum, int i, int hash, List<Integer> masks) {
        if(i>=nums.length)
            return;
        if(sum == nums[i]) {
            masks.add(hash|(1<<i));
        }
        getBitMasks(nums, sum, i+1, hash, masks);
        if (sum > nums[i]) {
            getBitMasks(nums, sum-nums[i], i+1, hash|(1<<i), masks);
        }
    }

    public static void main(String[] args) {
        int[] arr = {4,3,2,3,5,2,1};
        System.out.println(canPartitionKSubsets(arr , 4));

        int[] arr2= {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5};
        System.out.println(canPartitionKSubsets(arr2, 16));
    }
}
