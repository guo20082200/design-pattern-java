package com.zishi.algorithm.a07_tree.t05_huffman;

public class HuffmanNode implements Comparable<HuffmanNode> {

    private Byte b;
    private String value;// 0 1

    private int weight;

    private HuffmanNode left;
    private HuffmanNode right;

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



    public HuffmanNode(Byte b, String value, int weight) {
        this.b = b;
        this.value = value;
        this.weight = weight;
    }

    public HuffmanNode(Byte b, String value, int weight, HuffmanNode left, HuffmanNode right) {
        this.b = b;
        this.value = value;
        this.weight = weight;
        this.left = left;
        this.right = right;
    }

    public Byte getB() {
        return b;
    }

    public void setB(Byte b) {
        this.b = b;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    @Override
    public int compareTo(HuffmanNode o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "HuffmanNode{" +
                "b=" + b +
                ", value=" + value +
                ", weight=" + weight +
                '}';
    }


    /**
     * 中序遍历
     */
    public void midOrder() {
        if (this.getLeft() != null) {
            this.getLeft().midOrder();
        }
        System.out.println(this.getWeight());
        if (this.getRight() != null) {
            this.getRight().midOrder();
        }
    }
}
