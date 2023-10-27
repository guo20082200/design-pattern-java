// 传入某个数组，能输出该顺序存储二叉树的前，中，后序遍历结果

package com.zishi.algorithm.a07_tree.ercha;
 
import java.util.Arrays;
 
/**
 * @author yhx
 * @date 2020/10/14
 */
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};
        ArrBinaryTree tree = new ArrBinaryTree(arr);
        System.out.println("数组为："+ Arrays.toString(arr));
 
        System.out.println("数组的前序遍历结果为：");
        tree.preOrder();
        System.out.println();
 
        System.out.println("数组的中序遍历结果为：");
        tree.midOrder();
        System.out.println();
 
        System.out.println("数组的后序遍历结果为：");
        tree.postOrder();
        System.out.println();
    }
}
