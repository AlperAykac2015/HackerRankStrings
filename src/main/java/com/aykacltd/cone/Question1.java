package com.aykacltd.cone;

public class Question1 {

  public static ListNode reverseList3(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
      ListNode next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }
    return prev;
  }

  public static ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
      ListNode next = curr.next;
      curr.next = prev;
      prev = curr;
      curr = next;
    }
    return prev;
  }

  public static void printList(ListNode head) {
    ListNode curr = head;
    while (curr != null) {
      System.out.print(curr.val + (curr.next != null ? " -> " : ""));
      curr = curr.next;
    }
    System.out.println();
  }

  public static void main(String[] args) {
    // Build: 1 -> 2 -> 3 -> 4 -> 5
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);
    head.next.next.next.next = new ListNode(5);

    System.out.print("Original:  ");
    printList(head);

    head = reverseList(head);

    System.out.print("Reversed:  ");
    printList(head);
  }

  static class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
      this.val = val;
    }
  }
}
