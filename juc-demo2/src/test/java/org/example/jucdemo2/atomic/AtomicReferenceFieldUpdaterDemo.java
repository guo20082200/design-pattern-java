package org.example.jucdemo2.atomic;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterDemo {

    public static void main(String[] args) {

        Node left = new Node();
        left.setNumber(2);
        Node right = new Node();
        right.setNumber(3);
        Node node = new Node();
        node.setLeft(left);
        node.setRight(right);

        boolean compareAndSetLeft = node.compareAndSetLeft(left, right);
        System.out.println(compareAndSetLeft);
        System.out.println(node.getLeft().getNumber());

    }
}


@Data
class Node {
    private int number;
    private volatile Node left, right;
    private static final AtomicReferenceFieldUpdater<Node, Node> leftUpdater =
            AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "left");
    private static final AtomicReferenceFieldUpdater<Node, Node> rightUpdater =
            AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "right");

    /**
     * @param expect
     * @param update
     * @return
     */
    public boolean compareAndSetLeft(Node expect, Node update) {
        return leftUpdater.compareAndSet(this, expect, update);
    }
}