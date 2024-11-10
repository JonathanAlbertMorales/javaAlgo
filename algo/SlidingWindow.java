package algo;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class SlidingWindow {
    public int maxProfit(int[] prices) {
        int maxP = 0;
        int minBuy = prices[0];

        for (int sell : prices) {
            maxP = Math.max(maxP, sell - minBuy);
            minBuy = Math.min(minBuy, sell);
        }
        return maxP;
    }

    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> charSet = new HashSet<>();
        int l = 0;
        int res = 0;

        for (int r = 0; r < s.length(); r++) {
            while (charSet.contains(s.charAt(r))) {
                charSet.remove(s.charAt(l));
                l++;
            }
            charSet.add(s.charAt(r));
            res = Math.max(res, r - l + 1);
        }
        return res;
    }

    public int characterReplacement(String s, int k) {
        int res = 0;
        int maxf = 0;
        HashMap<Character, Integer> count = new HashMap<>();
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            count.put(s.charAt(i), count.getOrDefault(s.charAt(i), 0) + 1);
            maxf = Math.max(maxf, count.get(s.charAt(i)));
            while(i - left + 1 - maxf > k) {
                count.put(s.charAt(left), count.get(s.charAt(left)) - 1);
                left++;
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }

    public boolean checkInclusion(String s1, String s2) {
        if(s1.length() > s2.length()) return false;

        var s1Count = new int[26];
        var s2Count = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            s1Count[s1.charAt(i) - 'a']++;
            s2Count[s2.charAt(i)- 'a']++;
        }

        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if(s1Count[i] == s2Count[i]) {
                matches++;
            }
        }

        int l = 0;

        for(int r = s1.length(); r < s2.length(); r++) {
            if (matches == 26) {
                return true;
            }

            int index = s2.charAt(r) - 'a';
            s2Count[index]++;
            if (s1Count[index] == s2Count[index]) {
                matches++;
            } else if (s1Count[index] + 1 == s2Count[index]) {
                matches--;
            }

            index = s2.charAt(l) - 'a';
            s2Count[index]--;
            if (s1Count[index] == s2Count[index]) {
                matches++;
            } else if (s1Count[index] - 1 == s2Count[index]) {
                matches--;
            }
            l++;
        }

        return matches == 26;
    }

    public String minWindow(String s, String t) {
        if (t.isEmpty()) return "";

        var countT = new HashMap<Character, Integer>();
        var window = new HashMap<Character, Integer>();
        for(char c : t.toCharArray()) {
            countT.put(c, countT.getOrDefault(c, 0) + 1);
        }

        int have = 0, need = countT.size();
        int[] res ={-1, -1};
        int resLen = Integer.MAX_VALUE;
        int l = 0;

        for(int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            window.put(s.charAt(r), window.getOrDefault(s.charAt(r), 0  ) + 1);

            if(countT.containsKey(c) && window.get(c).equals(countT.get(c))) {
                have++;
            }

            while(have == need) {
                if(r - l + 1 < resLen) {
                    resLen = r- l  + 1;
                    res[0] = l;
                    res[1] = r;
                }

                char leftC = s.charAt(l);
                window.put(leftC, window.get(leftC) - 1);
                if(countT.containsKey(leftC) && window.get(leftC) < countT.get(leftC)) {
                    have--;
                }
                l++;
            }
        }

        return resLen == Integer.MAX_VALUE ? "" : s.substring(res[0], res[1] + 1);
    }

    public int[] maxSlidingWindow(int[] nums, int k) {

        int n = nums.length;
        int[] output = new int[n - k + 1];
        Deque<Integer> q = new LinkedList<>();
        int l = 0, r = 0;

        while (r < n) {
            while (!q.isEmpty() && nums[r] >  nums[q.getLast()]) {
                q.removeLast();
            }

            q.add(nums[r]);

            if(r+ 1 > k) {
                output[l] = nums[q.getFirst()];
                l++;
            }
            r++;
        }
        return output;
    }
}
