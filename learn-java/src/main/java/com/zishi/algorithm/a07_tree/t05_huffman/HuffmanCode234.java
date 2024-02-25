package com.zishi.algorithm.a07_tree.t05_huffman;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Huffman 编码
 *//*
public class HuffmanCode234 {


    public static final String CONTENT = "Returns an array containing all of the elements in this list in proper sequence (from first to last element); the runtime type of the returned array is that of the specified array. If the list fits in the specified array, it is returned therein. Otherwise, a new array is allocated with the runtime type of the specified array and the size of this list.";


    public static void main(String[] args) {

        HuffmanCode234 huffmanCode = new HuffmanCode234();

        Map<Character, Integer> integerMap = huffmanCode.characterCount(CONTENT);

        List<HuffmanCodeNode> huffmanCodeNodes = huffmanCode.codeNodeList(integerMap);
        HuffmanCodeNode codeNode = huffmanCode.buildHuffmanTree(huffmanCodeNodes);
        //System.out.println(codeNode);

        codeNode.levelIterator(codeNode);
        Map<Character, String> codeMap = Maps.newHashMap();

        List<String> strings = huffmanCode.binaryTreePaths(codeNode, codeMap);
        //strings.forEach(System.out::println);
        codeMap.forEach((k, v) -> {
            System.out.println(k + " " + v);
        });


        System.out.println(codeMap.size());
        System.out.println(strings.size());

        // 编码

        System.out.println(Arrays.toString("01100".getBytes()));
        byte[] encode = huffmanCode.encode(CONTENT, codeMap);
        //011001111000110001010101101011001000010101110100100100011101001010110101101001000010011101010100100010010000010100101101001000110100100011001111101110100101000001000001110100010101000111000100000001101110011010000001100001110011001000001001010111011010010001110001000000110100101011101000001101001010001011101101001000111011001001011010001011001000110101101110010100110000110100101011001100100010101000110111000011000010100010110100010000111011101010001101010110010100010111000101000101110100000100100101000101110011010000001100001110011001000001000011001011001100011100010000000110111010110101011001000001011010000111001101110001000010011001000110111010001010100011100010000000110111010110011000101010110101100100001101100010111010010101101011010010000100111011010010101110001000000100100010111010001010100011100010000000110111001010110010001101010100110101010001101001101100010111010010101101011010010000100110000001110110011100010100011100010000000110111010000011010010100010111010100011010001001010111011010010001110001000000011011100101011001000110101010011010101000110100110110001011101001010110101101001000010011001101011101101000101110110100101011101011001100010101011010110010000110110001011100010000000110101100110110100100011000000111011001110100010000000110101101100001011010010100110110011010111010010111001000011011000010111010010101101011010010000100111011010010101110100101000001000001000101010100100100010011011000101110110000101101000100000011100010000000110111010110101011001000001011010000111001101110001000010011001000110111010001010100011100010000000110111001010110010001101010100110101010001101001101100010111010010101101011010010000100111010010010001100010111000100000001101110010101101000011011001101110100010101000111000100000011010010101110100000110100101000101100000
        //System.out.println(encode);

        Map<String, Character> decodeMap = huffmanCode.decodeMap(codeMap);
        String decode = huffmanCode.decode(encode, decodeMap);
        System.out.println(decode.equals(CONTENT));
    }

    public static void zip(byte[] contentBytes) {
    }

    public Map<String, Character> decodeMap(Map<Character, String> codeMap) {
        Map<String, Character> res = Maps.newHashMap();
        for (Map.Entry<Character, String> characterStringEntry : codeMap.entrySet()) {
            res.put(characterStringEntry.getValue(), characterStringEntry.getKey());
        }
        return res;
    }

    public String decode(byte[] bytes, Map<String, Character> decodeMap) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i]);
        }
        String str = sb.toString();
        *//*for (Map.Entry<String, Character> entry : decodeMap.entrySet()) {
            String key = entry.getKey();
            Character value = entry.getValue();
        }*//*
        Set<String> keySet = decodeMap.keySet();
        StringBuilder resBuilder = new StringBuilder();
        int beginIndex = 0;
        while (beginIndex < str.length()) {
            for (String key : keySet) {
                int length = key.length();
                int endIndex = beginIndex + length;
                if (endIndex > str.length()) {
                    endIndex = str.length();
                }
                String substring = str.substring(beginIndex, endIndex);
                Character character = decodeMap.get(key);
                if (key.equals(substring)) { // 匹配
                    resBuilder.append(character);
                    beginIndex += length;
                }


            }
        }
        return resBuilder.toString();
    }

    *//**
     * 将字符串编码为字节数组
     *
     * @param content
     * @param codeMap
     * @return
     *//*
    public byte[] encode(String content, Map<Character, String> codeMap) {
        List<Byte> ls = Lists.newArrayList();
        int length = content.length();
        for (int i = 0; i < length; i++) {
            char charAt = content.charAt(i);
            String code = codeMap.get(charAt);
            for (int i1 = 0; i1 < code.length(); i1++) {
                ls.add((byte) code.charAt(i1));
            }
        }
        byte[] res = new byte[ls.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = ls.get(i);
        }
        return res;
    }

    // 统计频次
    public Map<Character, Integer> characterCount(String content) {

        Map<Character, Integer> map = Maps.newHashMap();
        int length = content.length();
        for (int i = 0; i < length; i++) {
            char charAt = content.charAt(i);
            Integer frequency = map.get(charAt);
            if (Objects.isNull(frequency)) {
                map.put(charAt, 1);
            } else {
                map.put(charAt, frequency + 1);
            }
        }
        return map;
    }

    // 2. 装换频次为List
    public List<HuffmanCodeNode> codeNodeList(Map<Character, Integer> map) {
        return map.entrySet().stream()
                .map(entry -> new HuffmanCodeNode(entry.getKey(), 0, entry.getValue(), null, null))
                .collect(Collectors.toList());
    }

    *//**
     * 构建huffman树
     *
     * @param nodeList
     * @return
     *//*
    public HuffmanCodeNode buildHuffmanTree(List<HuffmanCodeNode> nodeList) {


        // 因为每次都会remove一些节点，最终会在list中剩下一个节点，这个节点就是根节点
        while (nodeList.size() > 1) {
            // 从小到达排序list
            Collections.sort(nodeList);

            // 取出前两个最小的，第一个作为左节点，第二个作为右节点
            HuffmanCodeNode left = nodeList.get(0);
            left.setVal(0);
            HuffmanCodeNode right = nodeList.get(1);
            right.setVal(1);
            // 将权重+路径和赋值给父节点，将父节点的左右节点挂上

            HuffmanCodeNode parentNode = new HuffmanCodeNode(null, 0, left.getFrequency() + right.getFrequency(), left, right);
            parentNode.setLeft(left);
            parentNode.setRight(right);

            // 移除最小的两个节点，将父节点放入list集合中，进行下一轮
            nodeList.remove(left);
            nodeList.remove(right);
            nodeList.add(parentNode);
        }
        // 返回最终的根节点
        return nodeList.get(0);
    }

    public List<String> binaryTreePaths(HuffmanCodeNode root, Map<Character, String> map) {
        List<String> paths = new ArrayList<>();
        dfs(root, "", paths, map);
        return paths;
    }

    *//**
     * 根据哈夫曼树，左路为0，右路为1，获得编码表
     * 得到所有叶子节点对应的编码， 即码表
     *//*
    private void dfs(HuffmanCodeNode node, String path, List<String> paths, Map<Character, String> map) {
        // 如果是叶子节点，则将路径添加到结果集
        if (node == null) {
            return;
        }

        // 从根节点到达当前节点的路径
        path = path + node.getVal();

        // 如果是叶子节点，记录路径
        if (node.getLeft() == null && node.getRight() == null) {
            paths.add(path);
            map.put(node.getCh(), path);
        } else {
            // 不是叶子节点，继续深入左右子树
            // path += "->"; // 添加路径分隔符
            path += ""; // 添加路径分隔符
            dfs(node.getLeft(), path, paths, map);
            dfs(node.getRight(), path, paths, map);
        }
    }

}*/
