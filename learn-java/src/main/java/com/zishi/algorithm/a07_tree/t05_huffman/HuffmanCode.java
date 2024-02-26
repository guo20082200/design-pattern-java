package com.zishi.algorithm.a07_tree.t05_huffman;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HuffmanCode {

    private static Map<Byte, String> codeMap = Maps.newHashMap();
    private static Map<String, Byte> decodeMap = Maps.newHashMap();


    /**
     * 二叉树的层序遍历
     *
     * @param root
     */
    public void levelIterator(HuffmanNode root) {
        if (root == null) {
            return;
        }
        LinkedList<HuffmanNode> queue = new LinkedList<HuffmanNode>();
        HuffmanNode current = null;
        queue.offer(root);//将根节点入队
        while (!queue.isEmpty()) {
            current = queue.poll();//出队队头元素并访问
            System.out.print(current.getWeight() + "-->");
            if (current.getLeft() != null)//如果当前节点的左节点不为空入队
            {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null)//如果当前节点的右节点不为空，把右节点入队
            {
                queue.offer(current.getRight());
            }
        }

    }


    public static Map<Byte, Integer> byteCount(byte[] srcByte) {

        Map<Byte, Integer> byteCountMap = Maps.newHashMap();
        for (byte b : srcByte) {
            Integer count = byteCountMap.get(b);
            if (Objects.isNull(count)) {
                byteCountMap.put(b, 1);
            } else {
                byteCountMap.put(b, 1 + count);
            }
        }
        return byteCountMap;
    }

    public static List<HuffmanNode> convert2NodeList(Map<Byte, Integer> byteCountMap) {

        List<HuffmanNode> nodeList = Lists.newArrayList();
        for (Map.Entry<Byte, Integer> entry : byteCountMap.entrySet()) {
            HuffmanNode huffmanNode = new HuffmanNode(entry.getKey(), "", entry.getValue());
            nodeList.add(huffmanNode);
        }
        return nodeList;
    }

    public static HuffmanNode buildHuffmanTree(List<HuffmanNode> nodeList) {

        if (nodeList.isEmpty()) {
            return null;
        }

        if (nodeList.size() == 1) {
            return nodeList.get(0);
        }

        HuffmanNode left = nodeList.get(0);
        left.setValue("0");
        HuffmanNode right = nodeList.get(1);
        right.setValue("1");
        HuffmanNode parent = new HuffmanNode(null, "", left.getWeight() + right.getWeight(), left, right);
        nodeList.remove(0);
        nodeList.remove(0);
        nodeList.add(parent);
        return buildHuffmanTree(nodeList);
    }

    /**
     * 根据Huffman树获取对应的码表
     *
     * @param root Huffman树
     * @return
     */
    public static Map<Byte, String> codeMap(HuffmanNode root, Map<Byte, String> codeMap) {
        // Map<Byte, String> codeMap = Maps.newHashMap();
        if (root.getLeft() == null && root.getRight() == null) {
            // 表示叶子节点
            codeMap.put(root.getB(), root.getValue());
        } else {
            HuffmanNode left = root.getLeft();
            left.setValue(root.getValue() + left.getValue());
            codeMap(left, codeMap);
            HuffmanNode right = root.getRight();
            right.setValue(root.getValue() + right.getValue());
            codeMap(right, codeMap);
        }
        return codeMap;
    }


    public static byte[] encode(byte[] srcByte, Map<Byte, String> byteStringMap) {

        StringBuffer sb = new StringBuffer();
        for (byte b : srcByte) {
            String code = byteStringMap.get(b);
            sb.append(code);
        }

        String bitString = sb.toString();
        int singleLength = 8;

        int len = bitString.length() % 8 == 0 ? bitString.length() / 8 : bitString.length() / 8 + 1;
        byte[] zipHuffmanCodeBytes = new byte[len];
        for (int i = 0; i < zipHuffmanCodeBytes.length; i++) {
            int end = Math.min((i + 1) * singleLength, bitString.length());
            String strByte = bitString.substring(i * singleLength, end);
            zipHuffmanCodeBytes[i] = (byte) Integer.parseInt(strByte, 2);
        }
        return zipHuffmanCodeBytes;
    }

    public static byte[] compress(byte[] srcByte) {
        //1. 统计srcByte中每个byte出现的频次
        Map<Byte, Integer> byteIntegerMap = byteCount(srcByte);
        //2.构建HuffmanNode的集合，进而构建Huffman树
        List<HuffmanNode> nodeList = convert2NodeList(byteIntegerMap);
        HuffmanNode huffmanTree = buildHuffmanTree(nodeList);
        //System.out.println(huffmanTree.getWeight());
        //huffmanTree.midOrder();

        // 3. 构建码表
        Map<Byte, String> codeMap = Maps.newHashMap();


        assert huffmanTree != null;
        Map<Byte, String> byteStringMap = codeMap(huffmanTree, codeMap);
        //System.out.println(byteStringMap);
        HuffmanCode.codeMap = codeMap;

        // 转为反码表
        for (Map.Entry<Byte, String> byteStringEntry : HuffmanCode.codeMap.entrySet()) {
            decodeMap.put(byteStringEntry.getValue(), byteStringEntry.getKey());
        }

        // 4. 根据码表和字节数组，转换成为新的字节数组
        return encode(srcByte, byteStringMap);
    }


    public static byte[] uncompress(byte[] zipHuffmanBytes) {

        //1、先得到对应的二进制编码
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组转换成二进制字符串
        for (int i = 0; i < zipHuffmanBytes.length; i++) {
            byte b = zipHuffmanBytes[i];
            //判断是不是最后一个字节
            boolean flag = (i == zipHuffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag, b));
        }
        //2.  获取码表对应的反码表

        Map<String, Byte> decodeMap = HuffmanCode.decodeMap;


        //3. 根据反码表得到原字符串
        List<Byte> bits = Lists.newArrayList();
        String bitString = stringBuilder.toString();
        int length = bitString.length();// 00010110101011111111

        //System.out.println(length);
        for (int i = 0; i < length; ) {

            int count = 0;

            while (true) {
                String substring = bitString.substring(i, i + count);
                if (decodeMap.get(substring) != null) {
                    // 获取到对应的byte
                    Byte aByte = decodeMap.get(substring);
                    bits.add(aByte);
                    i += count;
                    count = 0;
                    break;
                } else {
                    count++;
                }
            }

        }

        // 4. 将集合转换为数组
        byte[] res = new byte[bits.size()];

        for (int i = 0; i < res.length; i++) {
            res[i] = bits.get(i);
        }
        return res;
    }


    /**
     * 将一个byte转成对应的二进制字符串
     *
     * @param flag 是否要补位的标志 true为需要补高位，false不用，最后一个字节不用补高位
     * @param b    传入的byte
     * @return byte对应的二进制字符串
     */
    public static String byteToBitString(boolean flag, byte b) {
        int temp = b;//使用变量保存byte并转换成int类型
        //如果是正数我们还需要补高位
        if (flag) {
            temp |= 256;//按位与256
        }
        String str = Integer.toBinaryString(temp);//返回的是temp的二进制补码
        if (flag) {
            return str.substring(str.length() - 8);//截取后八位
        } else {
            return str;
        }
    }


}
