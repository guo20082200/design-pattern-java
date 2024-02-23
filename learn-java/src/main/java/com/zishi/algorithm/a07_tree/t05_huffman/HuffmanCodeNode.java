package com.zishi.algorithm.a07_tree.t05_huffman;

import java.util.LinkedList;

public class HuffmanCodeNode implements Comparable<HuffmanCodeNode> {

    //ch存储当前节点字符，若没有，则为null
    private Character ch;
    //只有两个值：0或1，若在构建二叉树的过程中该节点为左节点，则val=0,反之，val=1
    private int val;
    //存储字符出现的频次
    private int frequency;
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
            System.out.print(current.frequency + "-->");
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
                "ch=" + ch +
                ", val=" + val +
                ", frequency=" + frequency +
                '}';
    }

    public HuffmanCodeNode(Character ch, int val, int frequency, HuffmanCodeNode left, HuffmanCodeNode right) {
        this.ch = ch;
        this.val = val;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(HuffmanCodeNode o) {
        return this.frequency - o.frequency;
    }


    public Character getCh() {
        return ch;
    }

    public void setCh(Character ch) {
        this.ch = ch;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
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
