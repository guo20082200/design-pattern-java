package com.zishi.algorithm.a07_tree.t09_23tree;

import java.util.function.Consumer;

/**
 * 23树的节点操作
 */
public class TwoThreeTreeNode<Key extends Comparable<Key>, Value> {

    private Key key1, key2; // 最多2个键
    private Value value1, value2; // 对应的值
    private TwoThreeTreeNode<Key, Value> left, middle, right; // 最多3个子节点
    private boolean isTwoNode; // 标记当前节点是否为2节点

    public TwoThreeTreeNode(Key k1, Value v1) {
        this.key1 = k1;
        this.value1 = v1;
        this.isTwoNode = true;
    }


    // 插入键值对
    public void insert(Key key, Value value) {
        if (isTwoNode) {
            // 当前节点为2节点时的插入逻辑
            if (key.compareTo(key1) < 0) {
                // 插入在第一个键左边，转化为3节点
                TwoThreeTreeNode<Key, Value> newNode = new TwoThreeTreeNode<>(key, value);
                newNode.right = this;
                this.left = newNode;
                isTwoNode = false;
            } else {
                // 插入在第一个键右边，先交换位置再转化为3节点
                Key tempKey = key1;
                Value tempValue = value1;
                key1 = key;
                value1 = value;
                insert(tempKey, tempValue);
            }
        } else {
            // 当前节点为3节点时的插入逻辑，这里仅提供简单插入到合适位置的逻辑，实际还需处理分裂等问题
            int comparison = key.compareTo(key1);
            if (comparison < 0) {
                left.insert(key, value);
            } else if (comparison > 0 && comparison < key2.compareTo(key)) {
                // 插入在两个键之间
                // 实际情况下，这里需要处理分裂操作，将3节点转换为两个2节点
                // TODO: 分裂和合并比较复杂。略去不写了。
                //
            } else {
                right.insert(key, value);
            }
        }
    }

    // 查找给定键的值
    public Value find(Key key) {
        if (isTwoNode) {
            // 2节点查找逻辑
            int comparison = key.compareTo(key1);
            if (comparison == 0) {
                return value1;
            } else if (comparison < 0 && left != null) {
                return left.find(key);
            } else if (comparison > 0 && right != null) {
                return right.find(key);
            } else {
                return null; // 键不存在
            }
        } else {
            // 3节点查找逻辑
            int comparisonWithKey1 = key.compareTo(key1);
            if (comparisonWithKey1 == 0) {
                return value1;
            } else {
                int comparisonWithKey2 = key.compareTo(key2);
                if (comparisonWithKey2 == 0) {
                    return value2;
                } else if (comparisonWithKey1 < 0 && left != null) {
                    return left.find(key);
                } else if (comparisonWithKey2 > 0 && right != null) {
                    return right.find(key);
                } else if (middle != null) { // 在key1和key2之间
                    return middle.find(key);
                } else {
                    return null; // 键不存在
                }
            }
        }
    }


    // 中序遍历（按升序）
    public void inorderTraversal(Consumer<Key> keyConsumer, Consumer<Value> valueConsumer) {
        if (this.isTwoNode) {
            // 2节点遍历逻辑
            if (left != null) {
                left.inorderTraversal(keyConsumer, valueConsumer);
            }
            keyConsumer.accept(key1);
            valueConsumer.accept(value1);
            if (right != null) {
                right.inorderTraversal(keyConsumer, valueConsumer);
            }
        } else {
            // 3节点遍历逻辑
            if (left != null) {
                left.inorderTraversal(keyConsumer, valueConsumer);
            }
            keyConsumer.accept(key1);
            valueConsumer.accept(value1);
            if (middle != null) {
                middle.inorderTraversal(keyConsumer, valueConsumer);
            }
            keyConsumer.accept(key2);
            valueConsumer.accept(value2);
            if (right != null) {
                right.inorderTraversal(keyConsumer, valueConsumer);
            }
        }
    }

    // 使用Java 8的Consumer接口，可以根据需求自定义消费行为
    public void inorderTraversal() {
        inorderTraversal(System.out::println, System.out::println);
    }
}
