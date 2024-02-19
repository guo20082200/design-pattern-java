package com.zishi.algorithm.a07_tree.t03_threadtree;

/**
 * 构建线索化二叉树的过程
 * 1. 构建一个正常的二叉树
 * 2. 对二叉树进行中序遍历，将所有的节点右子节点为空的指针域指向它的后继节点
 * 中序遍历是：左中右
 * 左节点的后继节点是：中
 * 右节点的后继节点是：当前节点的父节点的父节点（当前节点的父节点必须是当前节点的父节点的父节点的左子节点）
 * 3. 对二叉树进行中序遍历，将所有的节点左子节点为空的指针域指向它的前驱节点
 * 中序遍历是：左中右
 * 左节点的前驱节点是：当前节点的父节点的父节点（当前节点的父节点必须是当前节点的父节点的父节点的右子节点）
 * 右节点的前驱节点是：中
 */
public class ThreadTree {
    private ThreadNode root; // 根节点
    private int size; // 大小
    private ThreadNode pre = null; // 线索化的时候保存前驱

    public ThreadTree() {
        this.root = null;
        this.size = 0;
        this.pre = null;
    }

    public ThreadTree(int[] data) {
        this.pre = null;
        this.size = data.length;
        this.root = createTree(data, 1); // 创建二叉树
    }

    /**
     * 创建二叉树,将一个数组变为二叉树，并且是按照数组元素的顺序
     * 此方法非常的独特
     *
     * @param data  一维数组
     * @param index 开始索引的位置
     * @return 树
     */
    public ThreadNode createTree(int[] data, int index) {
        ThreadNode node = null;
        if (index <= data.length) {
            node = new ThreadNode(data[index - 1]);
            node.setLeft(createTree(data, 2 * index));
            node.setRight(createTree(data, 2 * index + 1));
        }
        return node;
    }

    /**
     * 将以root为根节点的二叉树线索化
     */
    public void inThread(ThreadNode root) {
        if (root != null) {
            inThread(root.getLeft()); // 线索化左子节点
            if (null == root.getLeft()) { // 左子节点为空
                root.setLeftTag(true); // 将左子节点设置为线索
                root.setLeft(pre);
            }
            if (pre != null && null == pre.getRight()) { // 右子节点为空
                pre.setRightTag(true);
                pre.setRight(root);
            }
            pre = root;
            inThread(root.getRight()); // 线索化右子节点
        }
    }

    /**
     * 中序遍历线索二叉树
     * <p>
     * 注释：后序线索化二叉树比较复杂
     */
    public void inThreadOrder(ThreadNode root) {
        // 如果node==null, 不能线索化
        if (root == null) {
            return;
        }

        //处理左子树
        inThreadOrder(root.getLeft());

        // 处理当前结点的前驱结点
        //左指针为空,将左指针指向前驱节点
        if (root.getLeft() == null) {
            root.setLeft(pre);
            root.setLeftTag(true);
        }

        //前一个节点的后继节点指向当前节点
        if (pre != null && pre.getRight() == null) {
            pre.setRight(root);
            pre.setRightTag(true);
        }
        //!!! 每处理一个结点后，让当前结点是下一个结点的前驱结点
        pre = root;
        System.out.println(pre.getData());
        //处理右子树
        inThreadOrder(root.getRight());
    }


    /**
     * 中序遍历线索二叉树，按照后继方式遍历（思路：找到最左子节点开始）
     * 链表可以双向遍历
     * @param node
     */
    void inThreadList(ThreadNode node) {
        //1、找中序遍历方式开始的节点
        while (node != null && !node.isLeftTag()) {
            node = node.getLeft();
        }

        while (node != null) {
            System.out.print(node.getData() + ", ");

            //如果右指针是线索
            if (node.isRightTag()) {
                node = node.getRight();

            } else {    //如果右指针不是线索，找到右子树开始的节点
                node = node.getRight();
                while (node != null && !node.isLeftTag()) {
                    node = node.getLeft();
                }
            }
        }
    }

    /**
     * 中序遍历线索二叉树，按照前驱方式遍历（思路：找到最右子节点开始倒序遍历）
     * 链表可以双向遍历
     * @param node
     */
    void inPreThreadList(ThreadNode node) {
        //1、找最后一个节点
        while (node.getRight() != null && !node.isRightTag()) {
            node = node.getRight();
        }

        while (node != null) {
            System.out.print(node.getData() + ", ");

            //如果左指针是线索
            if (node.isLeftTag()) {
                node = node.getLeft();

            } else {    //如果左指针不是线索，找到左子树开始的节点
                node = node.getLeft();
                while (node.getRight() != null && !node.isRightTag()) {
                    node = node.getRight();
                }
            }
        }
    }


    /**
     * 前序线索化二叉树
     *
     * @param node
     */
    void preThreadOrder(ThreadNode node) {
        if (node == null) {
            return;
        }
        //左指针为空,将左指针指向前驱节点
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftTag(true);
        }

        //前一个节点的后继节点指向当前节点
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightTag(true);
        }

        pre = node;

        //处理左子树
        if (!node.isLeftTag()) {
            preThreadOrder(node.getLeft());
        }

        //处理右子树
        if (!node.isRightTag()) {
            preThreadOrder(node.getRight());
        }
    }

    /**
     * 前序遍历线索二叉树（按照后继线索遍历）
     *
     * @param node
     */
    void preThreadList(ThreadNode node) {
        while (node != null) {

            while (!node.isLeftTag()) {
                System.out.print(node.getData() + ", ");
                node = node.getLeft();
            }

            System.out.print(node.getData() + ", ");
            node = node.getRight();
        }
    }


    /**
     * 中序递归遍历
     */
    public void middleTraverse(ThreadNode root) {
        if (root != null) {
            middleTraverse(root.getLeft());
            System.out.print(root.getData() + " ");
            middleTraverse(root.getRight());
        }
    }

    public void preTraverse(ThreadNode root) {
        if (root != null) {
            System.out.print(root.getData() + " ");
            preTraverse(root.getLeft());
            preTraverse(root.getRight());
        }
    }

    public void backTraverse(ThreadNode root) {
        if (root != null) {
            backTraverse(root.getLeft());
            backTraverse(root.getRight());
            System.out.print(root.getData() + " ");
        }
    }

    public ThreadNode getRoot() {
        return root;
    }

    public void setRoot(ThreadNode root) {
        this.root = root;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ThreadTree{" +
                "root=" + root +
                ", size=" + size +
                ", pre=" + pre +
                '}';
    }
}