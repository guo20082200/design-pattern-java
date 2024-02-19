package com.zishi.algorithm.a07_tree.t03_threadtree;

public class ThreadNode {
    private int data;
    private ThreadNode left;
    private boolean leftTag; // 左孩子是否为线索
    private ThreadNode right;
    private boolean rightTag; // 右孩子是否为线索

    public ThreadNode(int data) {
        this.data = data;
        this.left = null;
        this.leftTag = false;
        this.right = null;
        this.rightTag = false;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public ThreadNode getLeft() {
        return left;
    }

    public void setLeft(ThreadNode left) {
        this.left = left;
    }


    public boolean isLeftTag() {
        return leftTag;
    }

    public void setLeftTag(boolean leftTag) {
        this.leftTag = leftTag;
    }

    public boolean isRightTag() {
        return rightTag;
    }

    public void setRightTag(boolean rightTag) {
        this.rightTag = rightTag;
    }

    public ThreadNode getRight() {
        return right;
    }

    public void setRight(ThreadNode right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ThreadNode) {
            ThreadNode temp = (ThreadNode) obj;
            if (temp.getData() == this.data) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.data;
    }

    @Override
    public String toString() {
        return "ThreadNode{" +
                "data=" + data +
                ", left=" + left +
                ", leftTag=" + leftTag +
                ", right=" + right +
                ", rightTag=" + rightTag +
                '}';
    }
}