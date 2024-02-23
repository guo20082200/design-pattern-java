package com.zishi.algorithm.a07_tree.t05_huffman;

import com.google.common.collect.Lists;
import com.zishi.algorithm.a05_sort.QuickSort;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 赫夫曼树：
 *  哈夫曼树又称最优二叉树，是一种带权路径长度最短的二叉树。所谓树的带权路径长度，
 *  就是树中所有的叶结点的权值乘上其到根结点的路径长度
 *  （若根结点为0层，叶结点到根结点的路径长度为叶结点的层数）。
 * 构建思路：
 * 1. 将数列从小到大排序，此时每个数据就是一个节点
 * 2. 取出前两个节点，作为子节点，计算出父节点的权值(就是两个节点的权值和)
 * 3. 下一步就是将计算出的新父节点的权值放入数列中，重新排序，返回第二步
 * 4. 往复，最终会得到一个哈夫曼树
 */
public class HuffmanTree {

    public HuffmanTree(HuffmanNode root) {
        this.root = root;
    }

    private HuffmanNode root;

    public HuffmanNode getRoot() {
        return root;
    }

    public void setRoot(HuffmanNode root) {
        this.root = root;
    }


    public static void main(String[] args) {

        List<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5);

        HuffmanTree huffmanTree = new HuffmanTree(null);
        List<HuffmanNode> init = huffmanTree.init(integers);
        huffmanTree.build(init);
        System.out.println(init.get(0).getData());
    }

    // Collections.sort(collect);
    public List<HuffmanNode> init(List<Integer> ls) {
        return ls.stream().map(HuffmanNode::new).sorted().collect(Collectors.toList());
    }

    public void build(List<HuffmanNode> ls) {
        if (ls.size() == 1) {
            return;
        }
        Collections.sort(ls);
        List<HuffmanNode> temp = ls;
        int i = temp.get(0).getData();
        int j = temp.get(1).getData();
        int sum = i + j;
        HuffmanNode left = new HuffmanNode(i);
        HuffmanNode right = new HuffmanNode(j);
        HuffmanNode root = new HuffmanNode(left, right, sum);
        ls.remove(0);
        ls.remove(0);
        ls.add(root);
        build(ls);
    }
}
