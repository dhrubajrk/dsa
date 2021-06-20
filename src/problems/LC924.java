package problems;

/**
 * Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
 * word1 and word2 may be the same and they represent two individual words in the list.
 * For example,
 *  Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 *  Given word1 = “makes”, word2 = “coding”, return 1.
 *  Given word1 = "makes", word2 = "makes", return 3.
 */

public class LC924 {
    public int shortestDistance(String[] words, String word1, String word2) {
        // Write your code here
        int min = Integer.MAX_VALUE, word1Index = -1, word2Index = -1, prev = -1;
        for (int i = 0; i < words.length; ++i) {
            if (words[i].equals(word1))
                word1Index = i;
            if (words[i].equals(word2))
                word2Index = i;
            if (word1Index != -1 && word2Index != -1) {
                if (word1Index != word2Index) {
                    min = Math.min(min, Math.abs(word1Index - word2Index));
                } else {
                    min = Math.min(min, Math.abs(prev - word2Index));
                    prev = i;
                    word1Index = -1;
                    word2Index = -1;
                }
            }
        }
        return min;
    }
}
