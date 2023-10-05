package com.zishi.algorithm.a04_resursion;

public class MiGongTest01 {
    public static void main(String[] args) {
        //先创建一个二维数组
        //地图
        int[][] map = new int[8][7];
        //使用1表示墙
        //先把上下置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }

        //左右全部置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        //设置障碍
        map[3][1] = 1;
        map[3][2] = 1;
        //输出地图
        System.out.println("------MAP-----");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        //使用递归
        setWay(map, 1, 1);
        //输出新的地图，小球走过的通路
        System.out.println("------NEWMAP-----");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 如果小球可以到达map[6][5]，则说明通路找到
     * 约定：当map[i][j]为0表示该点没有走过，1的时候为墙；2的时候表示一个通路；3表示该点探测过了，但是不通
     * 走迷宫时候的策略：下 右 上 左，如果该点走不通在回溯
     *
     * @param map 地图
     * @param i   起始位置
     * @param j
     * @return
     */
    public static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            return true;
        } else {
            if (map[i][j] == 0) {//如果当前点还没有走过
                //按照策略走 下 右 上 左
                map[i][j] = 2;//假定该店可以走通
                if (setWay(map, i + 1, j)) {//先向下走
                    return true;
                } else if (setWay(map, i, j + 1)) {
                    return true;
                } else if (setWay(map, i - 1, j)) {
                    return true;
                } else if (setWay(map, i, j - 1)) {
                    return true;
                } else {
                    map[i][j] = 3;//说明该点是走不通的
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}

