package com.zishi.algorithm.a07_tree.t02_shunxu;

import java.util.Scanner;


public class BinaryTree {
    final private int DEFAULT_PEEK = 6;
    private int deep;
    private int length;
    private String[] array;

    /**
     * 无参构造器
     */
    public BinaryTree() {
        this.deep = DEFAULT_PEEK;
        this.length = (int) (Math.pow(2, deep) - 1);
        array = new String[length];
    }

    /**
     * 传入深度参数的构造器
     */
    public BinaryTree(int deep) {
        this.deep = deep;
        this.length = (int) (Math.pow(2, deep) - 1);
        array = new String[length];
    }

    /**
     * 传入参数深度和根结点的构造器
     */
    public BinaryTree(int deep, String data) {
        this.deep = deep;
        this.length = (int) (Math.pow(2, deep) - 1);
        array = new String[length];
        array[1] = data;
    }

    /**
     * 添加初始化结点
     */
    public void addNode() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入二叉树信息，输入值为0结束输入");
        for (int i = 1; i < array.length; i++) {
            if (array[i] == null || array[i].equals("0")) {
                array[i] = input.nextLine();
                if (array[i].equals("0")) {
                    break;
                }
            }
        }
    }

    /**
     * 判断二叉树是否为空
     */
    public boolean isEmpty() {
        if (array[1] != null && !array[1].equals("0")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 返回指定结点的值
     */
    public String returnData(int index) {
        if (index < 1 || index > array.length - 1) {
            return null;
        } else {
            return array[index];
        }
    }

    /**
     * 返回指定结点的父节点
     */
    public String getParent(int index) {
        if (index < 1 || index > array.length - 1) {
            return null;
        } else {
            return array[index / 2];
        }
    }

    /**
     * 添加指定结点的左节点
     */
    public void addNodeLeft(int index, String data) {
        if (array[2 * index] != null && !array[2 * index].equals("0")) {
            System.out.println("当前结点左节点已有值，是否覆盖？Y/N");
            Scanner input = new Scanner(System.in);
            String in = input.nextLine();
            if (in.equals("Y")) {
                array[index * 2] = data;
            } else if (in.equals("N")) {
                return;
            }
        }
    }

    /**
     * 添加指定结点的左节点
     */
    public void addNodeRight(int index, String data) {
        if (array[2 * index + 1] != null && !array[2 * index + 1].equals("0")) {
            System.out.println("当前结点右节点已有值，是否覆盖？Y/N");
            Scanner input = new Scanner(System.in);
            String in = input.nextLine();
            if (in.equals("Y")) {
                array[index * 2 + 1] = data;
            } else if (in.equals("N")) {
                return;
            }
        }
    }

    /**
     * 返回指定结点的左节点
     */
    public String getLeftNode(int index) {
        if (array[2 * index] != null && !array[2 * index].equals("0")) {
            return array[index * 2];
        } else {
            return null;
        }
    }

    /**
     * 返回指定结点的右节点
     */
    public String getRightNode(int index) {
        if (array[2 * index + 1] != null && !array[2 * index + 1].equals("0")) {
            return array[index * 2 + 1];
        } else {
            return null;
        }
    }
}