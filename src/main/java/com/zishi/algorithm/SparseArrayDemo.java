package com.zishi.algorithm;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 稀疏数组的问题
 * <p>
 * https://blog.csdn.net/qq_16855077/article/details/104168799
 */
public class SparseArrayDemo {


    /**
     * 把数组循环输出
     */
    public static void printArray(int[][] lists) {
        //使用jdk1.8新特性 ---第一种方法实现
        Arrays.stream(lists).forEach(i -> {
            Arrays.stream(i).forEach(n -> System.out.printf("%d\t", n));
            System.out.println();
        });
    }

    /**
     * 把二维数组转换成稀释数组
     * <p>
     * AtomicInteger标识原子性，也就是多线程过程中i++，高并发，假设1000个线程，每个都调用一次i++，最终的结果应该是1000，但是，不好意思，最终的结果可能会小于1000。可以把该字段设置为AtomicInteger，最终的结果一定是1000.
     * 第一步：求出sum
     * 第二步：创建稀疏数组
     * 第三步：把二维数组的行、列、有效数据，转为稀疏数组第一行的值。
     * 第四步，依次把二维数据对应的行、列、值映射到稀疏数组中。
     *
     * @param lists
     * @return
     */
    public static int[][] getSparseArray(int[][] lists) {
        if (lists.length < 0) {
            System.out.println("二维数组的长度不能为空");
            return null;
        }
        //第一步：求出sum
        AtomicInteger sum = new AtomicInteger();//记录有多少个非0的有效数据
        //得到稀疏数组
        Arrays.stream(lists).forEach(i -> {
            Arrays.stream(i).filter(o -> o != 0).forEach(n -> sum.getAndIncrement());
        });
        //第二步：创建稀疏数组
        int sparses[][] = new int[sum.get() + 1][3];
        //完成稀疏数组第一列
        sparses[0][0] = lists.length; //行数
        sparses[0][1] = lists[0].length;  //列数
        sparses[0][2] = sum.get();
        int count = 0;
        for (int x = 0; x < sparses[0][0]; x++) {
            for (int y = 0; y < sparses[0][1]; y++) {
                if (lists[x][y] != 0) {
                    count += 1;
                    sparses[count][0] = x;
                    sparses[count][1] = y;
                    sparses[count][2] = lists[x][y];
                }
            }
        }
        return sparses;
    }

    /**
     * 把稀疏数据保存为文件
     *
     * @param sparses
     * @param path
     */
    public static void sparseToFile(int[][] sparses, String path) {
        FileWriter fileWriter = null;
        try {
            File file = new File(path);
            if (file.exists()) {  //存在
                file.delete();  //则删除
            }
            //目录不存在 则创建
            if (!file.getParentFile().exists()) {
                boolean mkdir = file.getParentFile().mkdirs();
                if (!mkdir) {
                    throw new RuntimeException("创建目标文件所在目录失败！");
                }
            }
            file.createNewFile();

            fileWriter = new FileWriter(path);
            for (int[] row : sparses) {
                for (int item : row) {
                    fileWriter.write(item + "\t");
                }
                //\r\n即为换行
                fileWriter.write("\r\n");
            }
            // 把缓存区内容压入文件
            fileWriter.flush();
            System.out.println("稀疏数据保存文件成功!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 把文件转为稀疏数组
     *
     * @param path
     * @return
     */
    public static int[][] fileToSparse(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("文件转稀疏数据失败，文件不能为空!");
            return null;
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            String line = null;
            //缓存文件里面的值，再解析处理
            StringBuilder sb = new StringBuilder();
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println("行："+line);
                sb.append(line + "\r\n");
                count += 1;
            }
            //解析sb数据
            int sparses[][] = new int[count][3];
            String[] splits = sb.toString().split("\r\n");
            //第一行记录的是 二维数据的行和列，有效数据长度，不为有效数据
            for (int i = 0; i < splits.length; i++) {
                String[] temp = splits[i].split("\t");
                for (int j = 0; j < temp.length; j++) {
                    sparses[i][j] = Integer.parseInt(temp[j]);
                }
            }
            return sparses;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    bufferedReader = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 把稀疏数组转为二维数组
     *
     * @param fileToSparse
     * @return
     */
    public static int[][] sparseToArray(int[][] fileToSparse) {
        int[][] twoLists = new int[fileToSparse[0][0]][fileToSparse[0][1]];
        for (int i = 1; i < fileToSparse.length; i++) {
            twoLists[fileToSparse[i][0]][fileToSparse[i][1]] = fileToSparse[i][2];
        }
        return twoLists;
    }
}
