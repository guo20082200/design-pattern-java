package com.zishi.algorithm.a07_tree;

public class MainTest {

    public static void main(String[] args) {

        test02();
    }

    // 遍历测试
    public static void test01() {
        Binarytree tree = TreeUtil.init();
        System.out.println("前序："); //1 2 3 4
        tree.pre();
        System.out.println("中序："); // 2 1 3 4
        tree.mid();
        System.out.println("后序："); // 2 4 3 1
        tree.suf();
    }

    // 查找测试
    public static void test02() {
        Binarytree tree = TreeUtil.init();
        System.out.println("前序查找");
        Node res = tree.preSearch(2);
        if (res != null) {
            System.out.println("找到这个节点，no = " + res.getNo() + " , name = " + res.getName());
        } else {
            System.out.println("没有找到这个节点");
        }
        System.out.println("中序查找");
        res = tree.midSearch(5);
        if (res != null) {
            System.out.println("找到这个节点，no = " + res.getNo() + " , name = " + res.getName());
        } else {
            System.out.println("没有找到这个节点");
        }
        System.out.println("后序查找");
        res = tree.sufSearch(4);
        if (res != null) {
            System.out.println("找到这个节点，no = " + res.getNo() + " , name = " + res.getName());
        } else {
            System.out.println("没有找到这个节点");
        }

    }
}


