package com.zishi.algorithm.stack;

//链表
public class LinkedStack {
    //定义一个头节点
    Node head = new Node();

    public Node getHead() {
        return head;
    }

    //定义栈大小
    private int maxSize = 5;
    private int top = -1;

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(int value) {
        Node temp = head;
        if (isFull()) {
            return;
        }
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        Node res = new Node(value);
        temp.setNext(res);
        top++;
    }

    public void show() {
        Node temp = head.getNext();
        if (isEmpty()) {
            return;
        }
        while (temp != null) {
            System.out.print(temp.getData() + " ");
            temp = temp.getNext();
        }
        System.out.println();
    }

    public int pop() {
        Node temp = head;
        if (isEmpty()) {
            return -1;
        }
        top--;
        temp = temp.getNext();
        int res = temp.getData();
        System.out.println("出栈的数为" + res);
        return res;
    }

}