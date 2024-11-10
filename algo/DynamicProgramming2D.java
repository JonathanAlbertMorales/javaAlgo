package algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicProgramming2D {
    private int[][] memo;

            /*
     * Time complexity: O(n*m)
     * Space complexity: O(n*m)
     */
    public int longestCommonSubsequence(String text1, String text2) {
        memo = new int[text1.length()][text2.length()];
        for (int i = 0; i < text1.length(); i++) {
            for (int j = 0; j < text2.length(); j++) {
                memo[i][j] = -1;
            }
        }
        return dfs(text1, text2, 0, 0);
    }

    private int dfs(String text1, String text2, int i, int j) {
        if (i == text1.length() || j == text2.length()) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        if (text1.charAt(i) == text2.charAt(j)) {
            memo[i][j] = 1 + dfs(text1, text2, i + 1, j + 1);
        } else {
            memo[i][j] = Math.max(dfs(text1, text2, i + 1, j), 
                                  dfs(text1, text2, i, j + 1));
        }
        return memo[i][j];
    }
    
            /*
     * Time complexity: O(n*m)
     * Space complexity: O(n*m)
     */
    public int uniquePaths(int m, int n) {
        memo = new int[m][n];
        for(int[] it : memo) {
            Arrays.fill(it, -1);
        }
        return dfs(0, 0, n, m);
    }

    private int dfs(int i, int j, int n, int m) {
        if(i == m && j == n)  {
            return 1;
        }
        if (i >= m || j >= n) return 0;
        if(memo[i][j] != -1) return memo[i][j];

       var res =  dfs(i + 1, j, n, m) + dfs(i , j + 1, n, m);

       memo[i][j] = res;
       return res;
    }

    private Map<String, Integer> dp = new HashMap<>();
    
            /*
     * Time complexity: O(n)
     * Space complexity: O(n)
     */

    public int maxProfit(int[] prices) {
        return dfs(0, true, prices);
    }
    
    private int dfs(int i, boolean buying, int[] prices) {
        if (i >= prices.length) {
            return 0;
        }
        
        String key = i + "-" + buying;
        if (dp.containsKey(key)) {
            return dp.get(key);
        }

        int cooldown = dfs(i + 1, buying, prices);
        if (buying) {
            int buy = dfs(i + 1, false, prices) - prices[i];
            dp.put(key, Math.max(buy, cooldown));
        } else {
            int sell = dfs(i + 2, true, prices) + prices[i];
            dp.put(key, Math.max(sell, cooldown));
        }

        return dp.get(key);
    }

            /*
     * Time complexity: O(n*a)
     * Space complexity: O(n*a)
     */
    public int change(int amount, int[] coins) {
        Arrays.sort(coins);
         
        memo = new int[coins.length + 1][amount + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dfs(0, amount, coins, memo);
    }

    private int dfs(int i, int a, int[] coins, int[][] memo) {
        if (a == 0) return 1;
        if (i >= coins.length) return 0;
        if (memo[i][a] != -1) return memo[i][a];

        int res = 0;
        if (a >= coins[i]) {
            res = dfs(i + 1, a, coins, memo);
            res += dfs(i, a - coins[i], coins, memo);
        }
        memo[i][a] = res;
        return res;
    }

  
    private int[][] dpA;
    private int totalSum;

            /*
     * Time complexity: O(n*t)
     * Space complexity: O(n*t)
     */
    public int findTargetSumWays(int[] nums, int target) {
        totalSum = 0;
        for (int num : nums) totalSum += num;
        dpA = new int[nums.length][2 * totalSum + 1];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < 2 * totalSum + 1; j++) {
                dpA[i][j] = Integer.MIN_VALUE;
            }
        }
        return backtrack(0, 0, nums, target);
    }

    private int backtrack(int i, int total, int[] nums, int target) {
        if (i == nums.length) {
            return total == target ? 1 : 0;
        }
        if (dpA[i][total + totalSum] != Integer.MIN_VALUE) {
            return dpA[i][total + totalSum];
        }
        dpA[i][total + totalSum] = backtrack(i + 1, total + nums[i], nums, target) + 
                                  backtrack(i + 1, total - nums[i], nums, target);
        return dpA[i][total + totalSum];
    }

    private Boolean[][] dpBool;

            /*
     * Time complexity: O(n*m)
     * Space complexity: O(n*m)
     */

    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length(), n = s2.length();
        if (m + n != s3.length()) return false;
        dpBool = new Boolean[m + 1][n + 1];
        return dfs(0, 0, 0, s1, s2, s3);
    }
    
    private boolean dfs(int i, int j, int k, String s1, String s2, String s3) {
        if (k == s3.length()) {
            return (i == s1.length()) && (j == s2.length());
        }
        if (dpBool[i][j] != null) {
            return dpBool[i][j];
        }

        boolean res = false;
        if (i < s1.length() && s1.charAt(i) == s3.charAt(k)) {
            res = dfs(i + 1, j, k + 1, s1, s2, s3);
        }
        if (!res && j < s2.length() && s2.charAt(j) == s3.charAt(k)) {
            res = dfs(i, j + 1, k + 1, s1, s2, s3);
        }

        dpBool[i][j] = res;
        return res;
    }

                /*
     * Time complexity: O(n*m)
     * Space complexity: O(n*m)
     */
    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        dpA = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dpA[i][j] = -1;
            }
        }
        return dfs(0, 0, word1, word2, m, n);
    }

    private int dfs(int i, int j, String word1, String word2, int m, int n) {
        if (i == m) return n - j;
        if (j == n) return m - i;
        if (dpA[i][j] != -1) return dpA[i][j];

        if (word1.charAt(i) == word2.charAt(j)) {
            dpA[i][j] = dfs(i + 1, j + 1, word1, word2, m, n);
        } else {
            int res = Math.min(dfs(i + 1, j, word1, word2, m, n), 
                            dfs(i, j + 1, word1, word2, m, n));
            res = Math.min(res, dfs(i + 1, j + 1, word1, word2, m, n));
            dpA[i][j] = res + 1;
        }
        return dpA[i][j];
    }
}
