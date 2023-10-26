package com.zishi.algorithm.a07_tree;

import java.util.Objects;

public class Binarytree {
    private Node root;

    public void setRoot(Node root) {
        this.root = root;
    }

    // 前序
    public void pre() {
        if (this.root != null) {
            this.root.pre();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 中序
    public void mid() {
        if (this.root != null) {
            this.root.mid();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 后序
    public void suf() {
        if (this.root != null) {
            this.root.suf();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    // 前序查找
    public Node preSearch(int no) {
        return Objects.isNull(root)? null:root.preSearch(no);
    }

    // 中序查找
    public Node midSearch(int no) {
        return Objects.isNull(root)? null:root.midSearch(no);
    }

    // 后序查找
    public Node sufSearch(int no) {
        return Objects.isNull(root)? null:root.sufSearch(no);
    }

}
