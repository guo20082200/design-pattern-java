package com.zishi.algorithm.a07_tree.t05_huffman;

import java.util.LinkedList;

public class HuffmanCodeNode implements Comparable<HuffmanCodeNode> {

    //ch存储当前节点字符，若没有，则为null
    private Byte b;
    //只有两个值：0或1，若在构建二叉树的过程中该节点为左节点，则val=0,反之，val=1
    private int val;
    //存储字符出现的频次
    private int weight;
    //左节点
    private HuffmanCodeNode left;
    //右节点
    private HuffmanCodeNode right;


    /**
     * 二叉树的层序遍历
     *
     * @param root
     */
    public void levelIterator(HuffmanCodeNode root) {
        if (root == null) {
            return;
        }
        LinkedList<HuffmanCodeNode> queue = new LinkedList<HuffmanCodeNode>();
        HuffmanCodeNode current = null;
        queue.offer(root);//将根节点入队
        while (!queue.isEmpty()) {
            current = queue.poll();//出队队头元素并访问
            System.out.print(current.weight + "-->");
            if (current.left != null)//如果当前节点的左节点不为空入队
            {
                queue.offer(current.left);
            }
            if (current.right != null)//如果当前节点的右节点不为空，把右节点入队
            {
                queue.offer(current.right);
            }
        }

    }

    @Override
    public String toString() {
        return "HuffmanCodeNode{" +
                "ch=" + b +
                ", val=" + val +
                ", frequency=" + weight +
                '}';
    }

    public HuffmanCodeNode(Byte b, int val, int weight, HuffmanCodeNode left, HuffmanCodeNode right) {
        this.b = b;
        this.val = val;
        this.weight = weight;
        this.left = left;
        this.right = right;
    }

    public HuffmanCodeNode(Byte b, int val, int weight) {
        this.b = b;
        this.val = val;
        this.weight = weight;
    }

    @Override
    public int compareTo(HuffmanCodeNode o) {
        return this.weight - o.weight;
    }


    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }


    public Byte getB() {
        return b;
    }

    public void setB(Byte b) {
        this.b = b;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public HuffmanCodeNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanCodeNode left) {
        this.left = left;
    }

    public HuffmanCodeNode getRight() {
        return right;
    }

    public void setRight(HuffmanCodeNode right) {
        this.right = right;
    }

    public void infixOrder() {//中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }

    }

}
