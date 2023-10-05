package com.zishi.algorithm.a03_stack;


public class LinkedStackDemo {
    public static void main(String[] args) {
        LinkedStack linkedStack = new LinkedStack();
        linkedStack.push(1);
        linkedStack.push(2);
        linkedStack.show();
        Node head = linkedStack.getHead();
        reverseLinked(head);
        linkedStack.show();
    }

    public static void reverseLinked(Node head) {

        Node cur = head.getNext();
        Node next = null;
        Node reverse = new Node();
        while (cur != null) {
            next = cur.getNext();
            cur.setNext(reverse.getNext());
            reverse.setNext(cur);
            cur = next;
        }
        head.setNext(reverse.getNext());
    }
}


