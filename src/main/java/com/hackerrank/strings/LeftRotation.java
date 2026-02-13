package com.hackerrank.strings;

public class LeftRotation {
  public static int[] rotateLeft(int[] arr, int k) {
    int n = arr.length;
    if (n == 0) {
      return new int[] {};
    }
    k = k % n;
    if (k == 0) {
      return new int[] {};
    }

    int tmp = 0;
    for (int i = 0; i < k; i++) {
      var arrNew = new int[arr.length];
      tmp = arr[0];
      for (int j = 0; j < arr.length - 1; j++) {
        arrNew[j] = arr[j + 1];
      }
      arrNew[arr.length - 1] = tmp;
      arr = arrNew;
    }
    return arr;
  }

  public static void rotateLeftReversal(int[] arr, int k) {
    int n = arr.length;
    if (n == 0) {
      return;
    }
    k = k % n;
    if (k == 0) {
      return;
    }
    reverse(arr, 0, k - 1);
    reverse(arr, k, n - 1);
    reverse(arr, 0, n - 1);
  }

  private static void reverse(int[] arr, int left, int right) {
    while (left < right) {
      int tmp = arr[left];
      arr[left] = arr[right];
      arr[right] = tmp;
      left++;
      right--;
    }
  }

  public static int[] rotateLeftEfficiently(int[] arr, int k) {

    int size = arr.length;
    int[] rotated = new int[size];

    int idx = 0;
    for (int i = size - k; i < size; i++) {
      rotated[(i)] = arr[idx];
      idx++;
    }

    for (int i = 0; i < size - k; i++) {
      rotated[(i)] = arr[k + i];
    }

    return rotated;

  }


}
