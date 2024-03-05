package com.zishi.algorithm.a07_tree.t10_redblacktree;

public class MyRBTree<T extends Comparable<T>, D> {


    private RBNode<T, D> root;//根节点


    public RBNode<T, D> getRoot() {
        return root;
    }

    public void setRoot(RBNode<T, D> root) {
        this.root = root;
    }

    /**
     * 根据key获取数据
     *
     * @param key
     * @return
     */
    public D get(T key) {
        RBNode node = search(key, root);
        return node == null ? null : (D) node.data;
    }

    //寻找为key值的节点
    public RBNode<T, D> search(T key, RBNode<T, D> node) {

        if (node != null) {
            //查找的过程，就是一直递归比较到叶子为止
            int com = key.compareTo(node.key);
            if (com < 0) {
                return search(key, node.leftChild);
            } else if (com > 0) {
                return search(key, node.rightChild);
            } else {
                return node;
            }

        }
        return null;
    }


    //寻找后继节点，即大于该节点的最小节点
    public RBNode<T, D> min(RBNode<T, D> node) {

        //一直往左走，最左端的就是最小值，这是二叉树的性质
        if (node.leftChild == null) {
            return node;
        }
        while (node.leftChild != null) {
            node = node.leftChild;
        }

        return node;
    }

    /**
     * 寻找待删节点的后继节点
     * （因为这个节点即将要被删了，所以要选个后继节点补到这个位置来，
     * 选择节点的规则：
     * 这个规则和普通二叉树是一样的，要么就是找左子树的最大值，要么就是右子树的最小值）
     *
     * @param node
     * @return
     */
    public RBNode<T, D> successor(RBNode<T, D> node) {

        if (node.rightChild != null) {
            return min(node.rightChild);
        }
        //下面这里是不会进入的，因为只有node的两个孩子都不为null时才会进入这个方法
        RBNode<T, D> y = node.parent;
        while ((y != null) && (y.rightChild == node)) {
            node = y;
            y = y.parent;
        }
        return y;
    }

    /**
     * 对某个节点进行左旋
     * （当前节点就是父亲节点，整体过程就是 父亲下沉，右孩子上升，然后右孩子的左节点变成了原父亲的右节点）
     *
     * @param x
     */
    public void leftRotate(RBNode<T, D> x) {

        //右孩子
        RBNode<T, D> y = x.rightChild;

        if (y.leftChild != null) {
            //当前节点 变成了 右孩子的左节点的父亲
            y.leftChild.parent = x;
        }
        x.rightChild = y.leftChild;
        y.leftChild = x;
        //当前的父亲变成了右孩子的父亲
        y.parent = x.parent;

        if (x.parent != null) {
            if (x.parent.leftChild == x) {
                x.parent.leftChild = y;
            } else {
                x.parent.rightChild = y;
            }
        } else {
            this.root = y;
        }
        x.parent = y;
    }

    //对某个节点进行右旋
    public void rightRotate(RBNode<T, D> x) {
        RBNode<T, D> y = x.leftChild;

        if (y.rightChild != null) {
            y.rightChild.parent = x;

        }

        y.parent = x.parent;
        x.leftChild = y.rightChild;
        y.rightChild = x;

        if (x.parent != null) {
            if (x.parent.leftChild == x) {
                x.parent.leftChild = y;

            } else {
                x.parent.rightChild = y;
            }

        } else {
            this.root = y;
        }
        x.parent = y;

    }


}