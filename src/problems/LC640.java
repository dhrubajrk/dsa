package problems;

/**
 * Given two strings S and T, judge whether T can be obtained by editing S exactly once. You can choose any of the following operations for each edit:
 * Insert a character anywhere in S
 * Delete any character in S
 * Replace any character in S with other characters
 */
public class LC640 {
    public boolean isOneEditDistance(String s, String t) {
        if (Math.abs(s.length() - t.length())>1 )
            return false;
        // write your code here
        int minLen = Math.min(s.length(), t.length());
        for (int i=0; i< minLen; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (s.length() < t.length()) {
                    return s.substring(i).equals(t.substring(i+1));
                } else if (s.length() > t.length()) {
                    return s.substring(i+1).equals(t.substring(i));
                } else {
                    return s.substring(i+1).equals(t.substring(i+1));
                }
            }
        }
        return s.length()!=t.length();
    }
}
