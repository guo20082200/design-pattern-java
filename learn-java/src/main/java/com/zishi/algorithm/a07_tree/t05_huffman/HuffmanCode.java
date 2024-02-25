package com.zishi.algorithm.a07_tree.t05_huffman;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Huffman 编码
 */
public class HuffmanCode {


    private static Map<Byte, Integer> byteCount(byte[] contentBytes) {

        Map<Byte, Integer> map = Maps.newHashMap();
        for (byte contentByte : contentBytes) {
            Integer count = map.get(contentByte);
            if (Objects.isNull(count)) {
                map.put(contentByte, 1);
            } else {
                map.put(contentByte, 1 + count);
            }
        }
        return map;
    }

    private static List<HuffmanCodeNode> convert2NodeList(Map<Byte, Integer> byteCountMap) {
        List<HuffmanCodeNode> nodeList = Lists.newArrayList();
        for (Map.Entry<Byte, Integer> entry : byteCountMap.entrySet()) {
            HuffmanCodeNode node = new HuffmanCodeNode(entry.getKey(), 0, entry.getValue());
            nodeList.add(node);
        }
        return nodeList;
    }


    private static HuffmanCodeNode buildHuffmanTree(List<HuffmanCodeNode> nodeList) {

        if (nodeList.isEmpty()) {
            return null;
        }

        if (nodeList.size() == 1) {
            return nodeList.get(0);
        }

        Collections.sort(nodeList);
        HuffmanCodeNode left = nodeList.get(0);
        left.setVal(0);
        HuffmanCodeNode right = nodeList.get(1);
        right.setVal(1);
        nodeList.remove(0);
        nodeList.remove(0);
        HuffmanCodeNode parent = new HuffmanCodeNode(null, 0, left.getWeight() + right.getWeight(), left, right);
        nodeList.add(parent);
        return buildHuffmanTree(nodeList);
    }

    /**
     * 压缩字节数组
     *
     * @param contentBytes
     * @return
     */
    public static byte[] zip(byte[] contentBytes) {
        //1. 统计contentBytes出现的频次
        Map<Byte, Integer> byteCountMap = byteCount(contentBytes);
        // byteCountMap:{32=3, 97=4, 99=1, 101=1, 103=1, 105=2, 110=5, 111=1, 82=1, 114=3, 115=1, 116=2, 117=1, 121=1}
        System.out.println("byteCountMap:" + byteCountMap);
        //2. 根据频次构建Huffman树
        //2.1 构建HuffmanCodeNode的集合
        List<HuffmanCodeNode> huffmanCodeNodes = convert2NodeList(byteCountMap);
        //2.2 根据集合构建Huffman树
        HuffmanCodeNode huffmanTree = buildHuffmanTree(huffmanCodeNodes);
        System.out.println(huffmanTree.getWeight());
        huffmanTree.levelIterator(huffmanTree);

        //3. 根据huffman树找到字节对应的码表

        //4. 根据码表，将字节数组转换为新的字节数组

        return null;
    }


    /**
     * 解压
     *
     * @param zipBytes
     * @return
     */
    public static byte[] unzip(byte[] zipBytes) {

        return null;
    }
}
