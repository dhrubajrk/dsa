package problems;

/**
 * There are a row of  n  houses, each house can be painted with one of the  k  colors.
 * The cost of painting each house with a certain color is different. You have to paint all the houses such that no
 * two adjacent houses have the same color.
 *
 * The cost of painting each house with a certain color is represented by a  _n_  x  _k_ cost matrix. For example,
 * costs[0][0] is the cost of painting house 0 with color 0; costs[1][2] is the cost of painting house 1 with color 2,
 * and so on... Find the minimum cost to paint all houses.
 *
 * Note: All costs are positive integers.
 */
public class LC516 {
    public int minCostII(int[][] costs) {
        if (costs.length==0)
            return 0;

        // write your code here
        int n = costs.length;
        int k = costs[0].length;

        int min1 = 0, min2 = 0;
        int color1 = -1;

        for (int i =0;i<n;++i) {
            int currMin1 = Integer.MAX_VALUE, currMin2 = Integer.MAX_VALUE, currColor1=-1;
            for (int j = 0; j<k; ++j) {
                int val = costs[i][j] + (j == color1 ? min2 : min1);

                if (val < currMin1) {
                    currMin2 = currMin1;
                    currMin1 = val;
                    currColor1 = j;
                } else if (val < currMin2){
                    currMin2 = val;
                }
            }
            min1 = currMin1;
            min2 = currMin2;
            color1 = currColor1;
        }
        return min1;
    }
}
