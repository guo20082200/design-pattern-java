package com.zishi.algorithm.a07_tree.t10_redblacktree;

public class RBNode<T extends Comparable<T>, D> {

    Boolean color;//节点颜色
    T key;//键值
    D data;//具体的数据
    RBNode<T, D> parent;
    RBNode<T,D> leftChild;
    RBNode<T,D> rightChild;


    public RBNode(Boolean col, T key, D data, RBNode<T, D> parent, RBNode<T, D> leftChild, RBNode<T, D> rightChild) {
        this.color = col;
        this.key = key;
        this.data = data;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;

    }

    /**
     * 获取父亲
     *
     * @param node
     * @return
     */
    public RBNode<T, D> parentOf(RBNode<T, D> node) {
        if (node != null) {
            return node.parent;
        }
        return null;
    }

    /**
     * 获取颜色
     *
     * @param node
     * @return
     */
    public Boolean colorOf(RBNode<T, D> node) {
        if (node != null) {
            return node.color;

        }
        return Color.BLACK;

    }

    public Boolean isRed(RBNode<T, D> node) {
        return node != null && node.color == Color.RED;

    }

    public Boolean isBlack(RBNode<T, D> node) {
        return !isRed(node);

    }

    public Boolean getColor() {
        return color;
    }

    public void setColor(Boolean color) {
        this.color = color;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public RBNode<T, D> getParent() {
        return parent;
    }

    public void setParent(RBNode<T, D> parent) {
        this.parent = parent;
    }

    public RBNode<T, D> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(RBNode<T, D> leftChild) {
        this.leftChild = leftChild;
    }

    public RBNode<T, D> getRightChild() {
        return rightChild;
    }

    public void setRightChild(RBNode<T, D> rightChild) {
        this.rightChild = rightChild;
    }
}
