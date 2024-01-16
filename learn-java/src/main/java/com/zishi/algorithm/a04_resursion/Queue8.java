package com.zishi.algorithm.a04_resursion;

public class Queue8 {
    //定义一个max表示共有多少个皇后
    int max = 8;
    //定义一个数组array，保存皇后放置位置的结果，比如arr = {0，4，7，5，2，6，1，3}
    int[] array = new int[max];
    static int count = 0;
    static int judgeCount = 0;

    public static void main(String[] args) {
        Queue8 queue8 = new Queue8();
        queue8.check(0);
        System.out.println(count);
        System.out.println(judgeCount);
    }

    //编写一个方法，放置第n个皇后
    //check 每一次递归时，都有一次for循环，因此会有回溯
    private void check(int n) {
        if (n == max) {//开始放第九个皇后，放置结束
            print();
            count++;
            return;
        }
        //没有放置结束，依次放入皇后
        for (int i = 0; i < max; i++) {
            //先把当前皇后，放到该行的第1列
            array[n] = i;
            //判断是否可以放置
            if (judge(n)) {
                check(n + 1);
            }
            //如果冲突就继续执行for循环，直到全部列找完
        }
    }
    //查看当我们放置第n个皇后，就去检测该皇后是否和前面已经摆放完的皇后冲突

    /**
     * @param n 表示要放置的第n个皇后
     * @return
     */
    public boolean judge(int n) {
        judgeCount++;
        for (int i = 0; i < n; i++) {
            //第一个条件：判断是否在同一列
            //第二个条件：判断是否在同一斜线，如果在同一斜线当前行-行的绝对值 = 当前列- 列的绝对值
            //因为n是递增的所以一定不同一行
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    //写一个方法将最后摆放的位置打印出来
    private void print() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}

