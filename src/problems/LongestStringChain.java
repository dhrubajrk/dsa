package problems;

import java.util.Arrays;
import java.util.Comparator;

public class LongestStringChain {
    public static int longestStrChain(String[] words) {
        if (words.length == 0) {
            return 0;
        }
        int result = 1;
        int[] maxLengths = new int[words.length];
        maxLengths[0] = 1;
        Arrays.sort(words, Comparator.comparingInt(String::length));
        int prevStart = 0;
        int prevEnd = 0;
        int currStart = 0;
        for (int i = 1; i < words.length; ++i) {
            maxLengths[i] = 1;
            if (words[i].length() == words[i - 1].length() + 1) {
                prevStart = currStart;
                prevEnd = i - 1;
                currStart = i;
            } else if (words[i].length() > (words[i - 1].length() + 1)) {
                prevStart = i;
                prevEnd = i;
                currStart = i;
            }
            for (int j = prevStart; j <= prevEnd; ++j) {
                if (isPredecessor(words[j], words[i])) {
                    maxLengths[i] = Math.max(maxLengths[i], maxLengths[j] + 1);
                    result = Math.max(result, maxLengths[i]);
                }
            }
        }
        return result;
    }

    private static boolean isPredecessor(String a, String b) {
        if (a.length() != b.length() - 1)
            return false;
        for (int i = 0; i < b.length(); ++i) {
            if (i == a.length())
                return true;
            if (a.charAt(i) != b.charAt(i)) {
                return b.substring(i + 1).equals(a.substring(i));
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String[] words = {"","a","b","ba","bca","bda","bdca"};
        System.out.println(longestStrChain(words));
        String[] words1 = {"a", "bc", "abc", "afbc", "fed", "fedu", "feduh", "fediuh", "gfediuh", "gfediuhp"};
        System.out.println(longestStrChain(words1));
        String[] words2 = {"fedu", "feduh", "fediuh", "gfediuh", "gfediuhp"};
        System.out.println(longestStrChain(words2));
    }
}
