package com.zishi.algorithm.a07_tree.t08_balanced;

public class AVLTree <T extends Comparable<T>>{

    private final AVLNode<T> node;

    public AVLTree(AVLNode<T> node) {
        this.node = node;
    }

    public AVLNode<T> getNode() {
        return node;
    }

    /**
     * 左旋操作
     *  1. 节点的右孩子替代此节点位置
     *  2. 右孩子的左子树变为该节点的右子树
     *  3. 节点本身变为右孩子的左子树
     * @param y
     * @return
     */
    private AVLNode<T> leftRotate(AVLNode<T> y) {
        AVLNode<T> right = y.getRight();
        AVLNode<T> left = right.getLeft(); // 右孩子的左子树
        right.setLeft(y); // 节点本身变为右孩子的左子树
        y.setRight(left); // 右孩子的左子树变为该节点的右子树
        // 更新高度
        y.updateHeight();
        right.updateHeight();
        return right;
    }

    // 右旋操作
    private AVLNode<T> rightRotate(AVLNode<T> z) {
        AVLNode<T> y = z.getLeft();
        AVLNode<T> t3 = y.getRight();

        y.setRight(z);
        z.setLeft(t3);

        // 更新高度
        z.updateHeight();
        y.updateHeight();

        return y;
    }
}
