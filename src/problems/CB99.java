package problems;

import java.util.Scanner;

public class CB99 {
    public static void main(String args[]) {
        int t, h;
        Scanner s = new Scanner(System.in);
        t = s.nextInt();
        while (t-- > 0) {
            h = s.nextInt();
            int[][] arr = new int[h][5];
            for (int i = 0; i < h; ++i) {
                for (int j = 0; j < 5; ++j) {
                    arr[i][j] = s.nextInt();
                }
            }
            System.out.println(findMax(arr));
        }
        int[][] arr = new int[7][5];
        arr[0][0] = 1;
        arr[0][1] = 1;
        arr[0][2] = 1;
        arr[0][3] = 1;
        arr[0][4] = 1;
        arr[1][0] = -1;
        arr[1][1] = -1;
        arr[1][2] = 0;
        arr[1][3] = -1;
        arr[1][4] = -1;
        arr[2][0] = 1;
        arr[2][1] = -1;
        arr[2][2] = -1;
        arr[2][3] = -1;
        arr[2][4] = 0;
        arr[3][0] = 1;
        arr[3][1] = 0;
        arr[3][2] = 1;
        arr[3][3] = 0;
        arr[3][4] = 0;
        arr[4][0] = 0;
        arr[4][1] = 1;
        arr[4][2] = -1;
        arr[4][3] = 0;
        arr[4][4] = 0;
        arr[5][0] = 1;
        arr[5][1] = -1;
        arr[5][2] = -1;
        arr[5][3] = -1;
        arr[5][4] = -1;
        arr[6][0] = 1;
        arr[6][1] = -1;
        arr[6][2] = -1;
        arr[6][3] = -1;
        arr[6][4] = -1;
        System.out.println(findMax(arr));
    }

    private static int findMax(int[][] arr) {
        int h = arr.length;
        int[][][] compute = new int[h][5][7];
        int result = Integer.MIN_VALUE;
        for (int i = h - 1; i >= 0; --i) {
            for (int j = 0; j < 5; ++j) {
                for (int k = 0; k < 7; ++k) {
                    System.out.println(i + " " + j + " " + k);
                    compute[i][j][k] = Integer.MIN_VALUE;
                    if (k > h - i) {
                        continue;
                    }
                    if (i == h - 1) {
                        if (k == 0) {
                            compute[i][j][k] = arr[i][j];
                        } else if (k == 1) {
                            compute[i][j][k] = Math.max(0, arr[i][j]);
                        }
                        continue;
                    }
                    if (k < 1) {
                        int temp = Integer.MIN_VALUE;
                        if (j > 0)
                            temp = Math.max(temp, arr[i + 1][j - 1]);
                        if (j < 4)
                            temp = Math.max(temp, arr[i + 1][j + 1]);
                        temp = Math.max(temp, arr[i + 1][j]);
                        compute[i][j][k] = temp;
                    } else if (k > 5) {
                        int temp = Integer.MIN_VALUE;
                        if (j > 0)
                            temp = Math.max(Math.max(temp, compute[i + 1][j - 1][k - 1]), compute[i + 1][j - 1][k]);
                        if (j < 4)
                            temp = Math.max(Math.max(temp, compute[i + 1][j + 1][k - 1]), compute[i + 1][j + 1][k]);
                        temp = Math.max(Math.max(temp, compute[i + 1][j][k - 1]), compute[i + 1][j][k]);
                        compute[i][j][k] = temp + arr[i][j];
                    } else {
                        int temp = Integer.MIN_VALUE;
                        if (j > 0)
                            temp = Math.max(temp, compute[i + 1][j - 1][k - 1]);
                        if (j < 4)
                            temp = Math.max(temp, compute[i + 1][j + 1][k - 1]);
                        temp = Math.max(temp, compute[i + 1][j][k - 1]);
                        compute[i][j][k] = temp + Math.max(0, arr[i][j]);
                    }
                }
            }
        }

        for (int i = 0; i < 5; ++i) {
            result = Math.max(Math.max(Math.max(result, compute[0][i][0]), compute[0][i][5]), compute[0][i][6]);
        }
        return result;
    }
}
