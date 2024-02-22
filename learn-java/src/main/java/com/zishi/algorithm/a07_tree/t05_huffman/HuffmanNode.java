package com.zishi.algorithm.a07_tree.t05_huffman;

public class HuffmanNode implements Comparable<HuffmanNode> {

    /*@Override
    public int compare(HuffmanNode o1, HuffmanNode o2) {
        return 0;
    }*/


    public void preOrder() {//前序遍历
        System.out.print(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }

    }

    public void infixOrder() {//中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.print(this);
        if (this.right != null) {
            this.right.infixOrder();
        }

    }


    @Override
    public int compareTo(HuffmanNode o) {
        return this.data - o.data;
    }

    private HuffmanNode left;
    private HuffmanNode right;
    private int data;

    public HuffmanNode(int data) {
        this.data = data;
    }

    public HuffmanNode(HuffmanNode left, HuffmanNode right, int data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }


}
