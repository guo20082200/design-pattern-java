package com.zishi.algorithm.a07_tree.t07_binarysort;

public class BinarySortTree {
    BSTNode root;//根

    public BinarySortTree() {
        root = null;
    }

    public void preOrder() {
        root.preOrder();
    }

    public BinarySortTree(BSTNode root) {
        this.root = root;
    }

    /**
     * 寻找节点
     *
     * @param key 待查找的项目
     * @return
     */
    private BSTNode findNode(int key) {
        /*BSTNode current = root;
        while (current != null) {
            if (key == current.value) {
                return current;
            } else if (key < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;*/ // 如果找不到返回null
        return root.findNode(key);
    }

    /**
     * 变空
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * 查看是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * findMin()找到最小节点：
     * 因为所有节点的最小都是往左插入，所以只需要找到最左侧的返回即可。
     */
    public BSTNode findMin() {
        return root.findMin();
    }

    /**
     * findMax()找到最大节点
     * 因为所有节点大的都是往右面插入，所以只需要找到最右侧的返回即可。
     */
    public BSTNode findMax() {
        return root.findMax();
    }

    public boolean isContains(int target) {
        return root.isContains(target);
    }

    public boolean insert(int target) {
        return root.insert(target);
    }
    
    // 删除节点
    public void remove(int key) {
        BSTNode bstNode = root.removeRecursive(root, key);
    }

}
