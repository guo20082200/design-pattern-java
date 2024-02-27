package com.zishi.algorithm.a07_tree.t07_binarysort;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BinarySortTreeTest {

    private static final int[] arr = {5, 2, 3, 4, 1, 6, 9, 8, 7};

    BinarySortTree tree;

    @BeforeEach
    void setup() {
        BSTNode root = new BSTNode(arr[0]);

        for (int i = 1; i < arr.length; i++) {
            root.insert(arr[i]);
        }

        tree = new BinarySortTree(root);
        tree.preOrder();
    }

    @Test
    void makeEmpty() {
        tree.makeEmpty();
        Assertions.assertTrue(tree.isEmpty());
    }

    @Test
    void isEmpty() {
        Assertions.assertFalse(tree.isEmpty());
        tree.makeEmpty();
        Assertions.assertTrue(tree.isEmpty());
    }

    @Test
    void findMin() {
        BSTNode min = tree.findMin();
        Assertions.assertEquals(1, min.value);
    }

    @Test
    void findMax() {
        BSTNode max = tree.findMax();
        Assertions.assertEquals(9, max.value);
    }

    @Test
    void isContains() {
        Assertions.assertTrue(tree.isContains(9));
    }

    @Test
    void insert() {
        boolean insert = tree.insert(10);
        Assertions.assertTrue(insert);
        tree.preOrder();
    }

    @Test
    void delete() {

    }
}