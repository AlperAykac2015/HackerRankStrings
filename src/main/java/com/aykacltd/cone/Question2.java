package com.aykacltd.cone;

public class Question2 {

  /**
   * Best Time to Buy and Sell Stock II (multiple transactions allowed).
   * Greedy: sum every upward price difference.
   * Time: O(n), Space: O(1)
   */
  public static int maxProfit(int[] prices) {
    int profit = 0;
    for (int i = 1; i < prices.length; i++) {
      if (prices[i] > prices[i - 1]) {
        System.out.println("Buying at " + prices[i - 1] + " and selling at " + prices[i]);
        profit += prices[i] - prices[i - 1];
      }
    }
    return profit;
  }

  public static void main(String[] args) {
    int[] prices1 = {7, 1, 5, 3, 6, 4};
    System.out.println("Prices: [7,1,5,3,6,4] -> Max Profit: " + maxProfit(prices1)); // 7

    int[] prices2 = {1, 2, 3, 4, 5};
    System.out.println("Prices: [1,2,3,4,5] -> Max Profit: " + maxProfit(prices2)); // 4

    int[] prices3 = {7, 6, 4, 3, 1};
    System.out.println("Prices: [7,6,4,3,1] -> Max Profit: " + maxProfit(prices3)); // 0
  }
}
