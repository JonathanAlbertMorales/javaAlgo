package algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicProgramming1D {
    int[] cache;
             /*
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public int climbStairs(int n) {
        cache = new int[n];
        Arrays.fill(cache, -1);
        return dfs(n, 0); 
    }

    public int dfs(int n, int i) {
        if (i >= n) return i == n ? 1 : 0;
        if (cache[i] != -1) return cache[i];
        return cache[i] = dfs(n, i + 1) + dfs(n, i + 2);
    }

    private int[] memo;

             /*
     * Time complexity: O(n)
     * Space complexity: O(t)
     */
    public int rob(int[] nums) {
        int rob1 = 0, rob2 = 0;

        for (int num : nums) {
            int temp = Math.max(num + rob1, rob2);
            rob1 = rob2;
            rob2 = temp;
        }
        return rob2;
    }

    private int[][] memo2;
             /*
     * Time complexity: O(n)
     * Space complexity: O(t)
     */
    public int rob2(int[] nums) {
        return Math.max(nums[0], 
               Math.max(rob(Arrays.copyOfRange(nums, 1, nums.length)), 
               rob(Arrays.copyOfRange(nums, 0, nums.length - 1))));
    }

             /*
     * Time complexity: O(n^2)
     * Space complexity: O(n^2)
     */
    public String longestPalindrome(String s) {
        int resIdx = 0, resLen = 0;
        int n = s.length();

        boolean[][] dp = new boolean[n][n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && 
                    (j - i <= 2 || dp[i + 1][j - 1])) {
                        
                    dp[i][j] = true;
                    if (resLen < (j - i + 1)) {
                        resIdx = i;
                        resLen = j - i + 1;
                    }
                }
            }
        }

        return s.substring(resIdx, resIdx + resLen);
    }

      /*
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public int numDecodings(String s) {
        Map<Integer, Integer> dp = new HashMap<>();
        dp.put(s.length(), 1);

        return dfs(s, 0, dp);
    }

    private int dfs(String s, int i, Map<Integer, Integer> dp) {
        if (dp.containsKey(i)) {
            return dp.get(i);
        }
        if (s.charAt(i) == '0') {
            return 0;
        }

        int res = dfs(s, i + 1, dp);
        if (i + 1 < s.length() && (s.charAt(i) == '1' || 
           s.charAt(i) == '2' && s.charAt(i + 1) < '7')) {
            res += dfs(s, i + 2, dp);
        }
        dp.put(i, res);
        return res;
    }

          /*
     * Time complexity: O(n*t)
     * Space complexity: O(t)
     */
    public int dfsCoins(int[] coins, int amount) {
        if (amount == 0) return 0;
        if (memo[amount] != -1) return memo[amount];

        int res = (int) 1e9;
        for (int coin : coins) {
            if (amount - coin >= 0) {
                res = Math.min(res, 
                      1 + dfsCoins(coins, amount - coin));
                      
                   
            }
        }
        memo[amount] = res;
        return res;
    }

    public int coinChange(int[] coins, int amount) {
        memo = new int[coins.length];
        Arrays.fill(coins, -1);
        int minCoins = dfsCoins(coins, amount);
        return (minCoins >= 1e9) ? -1 : minCoins;
    }

             /*
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public int maxProduct(int[] nums) {
        int res = nums[0];
        int curMin = 1, curMax = 1;

        for (int num : nums) {
            int tmp = curMax * num;
            curMax = Math.max(Math.max(num * curMax, num * curMin), num);
            curMin = Math.min(Math.min(tmp, num * curMin), num);
            res = Math.max(res, curMax);
        }
        return res;
    }

     private Map<Integer, Boolean> memoMap;

              /*
     * Time complexity: O((t^2*n) + m)
     * Space complexity: O(n + (m*t))
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        memoMap = new HashMap<>();
        memoMap.put(s.length(), true);
        return dfs(s, wordDict, 0);
    }

    private boolean dfs(String s, List<String> wordDict, int i) {
        if (memoMap.containsKey(i)) {
            return memoMap.get(i);
        }

        for (String w : wordDict) {
            if (i + w.length() <= s.length() && 
                s.substring(i, i + w.length()).equals(w)) {
                if (dfs(s, wordDict, i + w.length())) {
                    memoMap.put(i, true);
                    return true;
                }
            }
        }
        memoMap.put(i, false);
        return false;
    }

       /*
     * Time complexity: O(n^2)
     * Space complexity: O(n^2)
     */
    private int dfsLIS(int i, int j, int[] nums) {
        if (i == nums.length) {
            return 0;
        }
        if (memo2[i][j + 1] != -1) {  
            return memo2[i][j + 1];
        }

        int LIS = dfsLIS(i + 1, j, nums);

        if (j == -1 || nums[j] < nums[i]) {
            LIS = Math.max(LIS, 1 + dfsLIS(i + 1, i, nums));
        }

        memo2[i][j + 1] = LIS;
        return LIS;
    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        memo2 = new int[n][n + 1];  
        for (int[] row : memo2) {
            Arrays.fill(row, -1);  
        }
        return dfsLIS(0, -1, nums);
    }

    Boolean[][] memoBool;

       /*
     * Time complexity: O(n * target)
     * Space complexity: O(n* target)
     */

    public boolean canPartition(int[] nums) {
        int n = nums.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        if (sum % 2 != 0) {
            return false;
        }
        memoBool = new Boolean[n][sum / 2 + 1];
        
        return dfs(nums, 0, sum / 2);
    }

    public boolean dfs(int[] nums, int i, int target) {
        if (i == nums.length) {
            return target == 0;
        }
        if (target < 0) {
            return false;
        }
        if (memoBool[i][target] != null) {
            return memoBool[i][target];
        }

        memoBool[i][target] = dfs(nums, i + 1, target) || 
                          dfs(nums, i + 1, target - nums[i]);
        return memoBool[i][target];
    }
}
