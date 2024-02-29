package com.zishi.algorithm.a07_tree.t08_balanced;

public class AVLNode<T extends Comparable<T>> {

    private T data;            // 节点的关键字值
    private int height;         // 节点的高度
    private AVLNode<T> left;       // 左子节点
    private AVLNode<T> right;      // 右子节点

    public void midOrder() {
        if (this.getLeft() != null) {
            this.getLeft().midOrder();
        }
        System.out.println(this.getData() + " " + this.getHeight());
        if (this.getRight() != null) {
            this.getRight().midOrder();
        }
    }

    public AVLNode(T data) {
        this.data = data;
        left = null;
        right = null;
        height = 1; // 新节点初始高度为1
    }

    // 计算节点的平衡因子
    public int getBalance() {
        return height(left) - height(right);
    }

    // 计算子树高度
    private int height(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }

    // 更新节点高度
    public void updateHeight() {
        height = 1 + Math.max(height(left), height(right));
    }

    // 寻找节点的最大值（用于删除节点后替代父节点值）
    public AVLNode<T> findMaxInLeftSubtree() {
        AVLNode<T> current = this;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public AVLNode<T> getLeft() {
        return left;
    }

    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    public AVLNode<T> getRight() {
        return right;
    }

    public void setRight(AVLNode<T> right) {
        this.right = right;
    }
}
