package com.zishi.algorithm.a07_tree.t01_binary;

/**
 * 实现顺序存储二叉树遍历
 */
public class ArrBinaryTree {
    private final int[] arr;

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    /**
     * 使用前序遍历时，一般都从0下标开始，为了函数调用方便，
     * 此处重写一个preOrder()，这个无参函数调用有参函数preOrder(0)
     */
    public void preOrder() {
        this.preOrder(0);
    }

    public void midOrder() {
        this.midOrder(0);
    }

    public void postOrder() {
        this.postOrder(0);
    }

    /**
     * 顺序存储二叉树的前序遍历
     *
     * @param index 数组的下标
     */
    public void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }
        assert arr != null;
        System.out.printf(" -> %d", arr[index]);
 
        /*
         向左递归遍历
         第n个元素的左子节点为 2*n+1
         */
        if ((index * 2 + 1) < arr.length) {
            preOrder(2 * index + 1);
        }
 
        /*
         向右递归遍历
         第n个元素的右子节点为 2*n+2
         */
        if ((index * 2 + 2) < arr.length) {
            preOrder(2 * index + 2);
        }
    }

    /**
     * 顺序存储二叉树的中序遍历
     *
     * @param index 数组的下标
     */
    public void midOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }
 
        /*
         向左递归遍历
         第n个元素的左子节点为 2*n+1
         */
        assert arr != null;
        if ((index * 2 + 1) < arr.length) {
            midOrder(2 * index + 1);
        }

        System.out.printf(" -> %d", arr[index]);
 
        /*
         向右递归遍历
         第n个元素的右子节点为 2*n+2
         */
        if ((index * 2 + 2) < arr.length) {
            midOrder(2 * index + 2);
        }
    }

    /**
     * 顺序存储二叉树的后序遍历
     *
     * @param index 数组的下标
     */
    public void postOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }
 
        /*
         向左递归遍历
         第n个元素的左子节点为 2*n+1
         */
        assert arr != null;
        if ((index * 2 + 1) < arr.length) {
            postOrder(2 * index + 1);
        }
 
        /*
         向右递归遍历
         第n个元素的右子节点为 2*n+2
         */
        if ((index * 2 + 2) < arr.length) {
            postOrder(2 * index + 2);
        }

        System.out.printf(" -> %d", arr[index]);
    }
}