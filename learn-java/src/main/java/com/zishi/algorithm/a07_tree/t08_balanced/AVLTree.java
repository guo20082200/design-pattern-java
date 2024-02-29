package com.zishi.algorithm.a07_tree.t08_balanced;

public class AVLTree<T extends Comparable<T>> {

    public void midOrder() {
        root.midOrder();
    }

    private AVLNode<T> root;

    public AVLTree(AVLNode<T> root) {
        this.root = root;
    }

    public AVLNode<T> getNode() {
        return root;
    }

    // 插入节点并保持平衡
    public void insert(T data) {
        root = insertRec(root, data);
    }

    private AVLNode<T> insertRec(AVLNode<T> node, T data) {
        if (node == null) {
            return new AVLNode<>(data);
        }

        int cmp = data.compareTo(node.getData());

        if (cmp < 0) {
            node.setLeft(insertRec(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(insertRec(node.getRight(), data));
        } else { // 数据已存在，AVL树不允许重复数据
            return node;
        }

        // 更新节点高度
        node.updateHeight();

        // 检查是否需要平衡调整
        int balance = node.getBalance();

        // 左左失衡
        if (balance > 1 && data.compareTo(node.getLeft().getData()) < 0) {
            return rightRotate(node);
        }

        // 右右失衡
        if (balance < -1 && data.compareTo(node.getRight().getData()) > 0) {
            return leftRotate(node);
        }

        // 左右或右左失衡
        if (balance > 1 && data.compareTo(node.getLeft().getData()) > 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        if (balance < -1 && data.compareTo(node.getRight().getData()) < 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }
        return node;
    }

    // 删除节点
    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private AVLNode<T> deleteRec(AVLNode<T> node, T data) {
        if (node == null) {
            return null;
        }

        int cmp = data.compareTo(node.getData());
        if (cmp < 0) {
            node.setLeft(deleteRec(node.getLeft(), data));
        } else if (cmp > 0) {
            node.setRight(deleteRec(node.getRight(), data));
        } else { // 找到要删除的节点
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }

            // 有两个子节点，找到右子树的最小节点作为替代节点
            AVLNode<T> successor = node.getRight();
            AVLNode<T> predecessorParent = node;
            while (successor.getLeft() != null) {
                predecessorParent = successor;
                successor = successor.getLeft();
            }

            // 替代节点值
            node.setData(successor.getData());

            // 删除替代节点
            if (predecessorParent.getLeft() == successor) {
                predecessorParent.setLeft(successor.getRight());
            } else {
                predecessorParent.setRight(successor.getRight());
            }

            // 继续删除原替代节点的位置
            node = node.getRight();
        }

        // 更新节点高度
        node.updateHeight();

        // 检查是否需要平衡调整
        int balance = node.getBalance();

        // 左左失衡
        if (balance > 1 && node.getLeft().getBalance() >= 0) {
            return rightRotate(node);
        }

        // 右右失衡
        if (balance < -1 && node.getRight().getBalance() <= 0) {
            return leftRotate(node);
        }

        // 左右或右左失衡
        if (balance > 1 && node.getLeft().getBalance() < 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        if (balance < -1 && node.getRight().getBalance() > 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }
        return node;
    }


    /**
     * 左旋操作
     * 1. 节点的右孩子替代此节点位置
     * 2. 右孩子的左子树变为该节点的右子树
     * 3. 节点本身变为右孩子的左子树
     *
     * @param y
     * @return
     */
    private AVLNode<T> leftRotate(AVLNode<T> y) {
        AVLNode<T> right = y.getRight();
        AVLNode<T> rightLeft = right.getLeft(); // 右孩子的左子树
        right.setLeft(y); // 节点本身变为右孩子的左子树
        y.setRight(rightLeft); // 右孩子的左子树变为该节点的右子树
        // 更新高度
        y.updateHeight();
        right.updateHeight();
        return right;
    }

    /**
     * 右旋操作
     * 1. 节点的左孩子代表此节点
     * 2. 节点的左孩子的右子树变为节点的左子树
     * 3. 将此节点作为左孩子节点的右子树
     *
     * @param z
     * @return
     */
    private AVLNode<T> rightRotate(AVLNode<T> z) {
        AVLNode<T> left = z.getLeft(); // 左子树
        AVLNode<T> leftRight = left.getRight(); //左孩子的右子树

        left.setRight(z); // 3. 将此节点作为左孩子节点的右子树
        z.setLeft(leftRight); //2. 节点的左孩子的右子树变为节点的左子树

        // 更新高度
        z.updateHeight();
        left.updateHeight();
        return left;
    }
}
