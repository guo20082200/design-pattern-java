package com.zishi.algorithm.a07_tree.t07_binarysort;

public class BSTNode {//结点
    public int value;
    public BSTNode left;
    public BSTNode right;

    public BSTNode() {
    }

    public BSTNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public BSTNode(int value, BSTNode l, BSTNode r) {
        this.value = value;
        this.left = l;
        this.right = r;
    }


    public BSTNode findNode(int key) {
        BSTNode current = this;
        while (current != null) {
            if (key == current.value) {
                return current;
            } else if (key < current.value) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    public BSTNode findMin() {
        return this.left == null ? this : this.left.findMin();
    }

    public BSTNode findMin(BSTNode node) {
        return node.left == null ? node : node.left.findMin();
    }

    // 查找最小值节点（用于替换待删除节点）
    private BSTNode findMinNode(BSTNode node) {
        if (node.left == null)
            return node;
        return findMinNode(node.left);
    }

    public BSTNode findMax() {
        return this.right == null ? this : this.right.findMax();
    }

    public BSTNode findMax(BSTNode root) {
        return root.right == null ? root : root.right.findMax();
    }

    public boolean isContains(int target) {
        BSTNode current = this;
        if (current.value > target) {
            if (current.left != null) {
                return current.left.isContains(target);
            } else {
                return false;
            }
        } else if (this.value < target) {
            if (current.right != null) {
                return current.right.isContains(target);
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 插入 t是root的引用
     *
     * @param target
     * @return
     */
    public boolean insert(int target) {

        BSTNode current = this;
        boolean contains = isContains(target);
        if (contains) {
            return false;
        } else {
            if (target < current.value) {
                if (current.left == null) {
                    current.left = new BSTNode(target);
                    return true;
                } else {
                    current = current.left;
                    return current.insert(target);
                }
            } else if (target > current.value) {
                if (current.right == null) {
                    current.right = new BSTNode(target);
                    return true;
                } else {
                    current = current.right;
                    return current.insert(target);
                }
            } else {
                return false;
            }
        }
    }



    /**
     * 1. 删除的节点没有左右子节点: 这种情况不需要考虑，直接删除即可。
     * 2. 删除的节点没有左子节点，有右子节点: 将删除节点的子节点放到删除的位置即可
     * 3. 删除的节点没有右子节点，有左子节点: 将删除节点的子节点放到删除的位置即可
     * 4. 删除的节点即有右子节点，又有左子节点:
     * a. 找到待删除节点的左子树的最大值，并删除，然后替换当前节点的值为找到的最大值即可
     * a. 找到待删除节点的右子树的最小值，并删除，然后替换当前节点的值为找到的最小值即可
     *
     * @param current
     * @param key
     * @return
     */
    public BSTNode removeRecursive(BSTNode current, int key) {
        if (current == null) {
            return null;
        }

        if (key < current.value) {
            current.left = removeRecursive(current.left, key);
        } else if (key > current.value) {
            current.right = removeRecursive(current.right, key);
        } else { // 找到了待删除节点
            if (current.left == null) {
                return current.right; // 没有左孩子，直接返回右孩子
            } else if (current.right == null) {
                return current.left; // 没有右孩子，直接返回左孩子
            } else {
                // 有两个孩子，找到右子树的最小节点来替换当前节点
                BSTNode smallestNode = findMin(current.right);
                current.value = smallestNode.value; // 替换值
                current.right = removeRecursive(current.right, smallestNode.value); // 删除替换的最小节点
            }
        }
        return current;
    }


    /**
     * 中序遍历
     */
    public void preOrder() {

        if (this.left != null) {
            this.left.preOrder();
        }
        System.out.print(this);
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    @Override
    public String toString() {
        return " " + value;
    }


}
