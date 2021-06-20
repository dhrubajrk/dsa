package problems;

/**
 * Given a string, find the longest substringTTThe length, it contains at 2most different characters.
 */
public class LC928 {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.length() <= 2)
            return s.length();
        int a = 0;
        int result = 2;
        int currLength = 2;
        for (int i = 2; i < s.length(); ++i) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                currLength += 1;
            } else if (s.charAt(i) == s.charAt(a) || s.charAt(i - 1) == s.charAt(a)) {
                a = i - 1;
                currLength += 1;
            } else {
                result = Math.max(result, currLength);
                currLength = i - a;
                a = i - 1;
            }
        }
        return Math.max(result, currLength);
    }
}
