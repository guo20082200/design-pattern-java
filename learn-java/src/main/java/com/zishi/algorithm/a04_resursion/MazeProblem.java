package com.zishi.algorithm.a04_resursion;


public class MazeProblem {
    public static int[][] mapArray = new int[][]{
            new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            new int[]{1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1},
            new int[]{1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
            new int[]{1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1},
            new int[]{1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            new int[]{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1},
            new int[]{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            new int[]{1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
            new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1},
            new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1},
            new int[]{1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1},
            new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    public static int[] start = {1, 1};
    public static int[] end = {10, 12};

    public void test01() {
        //输出地图
        System.out.println("------MAP-----");
        for (int i = 0; i < mapArray.length; i++) {
            for (int j = 0; j < mapArray[0].length; j++) {
                System.out.print(mapArray[i][j] + " ");
            }
            System.out.println();
        }
        //使用递归
        setWay(mapArray, start[0], start[1], end);
        //输出新的地图，小球走过的通路
        System.out.println("------NEWMAP-----");
        for (int i = 0; i < mapArray.length; i++) {
            for (int j = 0; j < mapArray[0].length; j++) {
                System.out.print(mapArray[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 如果小球可以到达end，则说明通路找到
     * 约定：当map[i][j]为0表示该点没有走过，1的时候为墙；2的时候表示一个通路；3表示该点探测过了，但是不通
     * 走迷宫时候的策略：下 右 上 左，如果该点走不通在回溯
     *
     * @param mapArray 地图
     * @param startx,  starty   起始位置
     * @param end      结束位置
     * @return
     */
    private boolean setWay(int[][] mapArray, int startx, int starty, int[] end) {
        if (mapArray[end[0]][end[1]] == 2) {
            return true;
        } else {
            if (mapArray[startx][starty] == 0) {//如果当前点还没有走过
                //按照策略走 下 右 上 左
                mapArray[startx][starty] = 2;//假定该店可以走通
                if (setWay(mapArray, startx, starty + 1, end)) {//先向右走
                    return true;
                } else if (setWay(mapArray, startx + 1, starty, end)) { // 向下
                    return true;
                } else if (setWay(mapArray, startx - 1, starty, end)) { // 向上
                    return true;
                } else if (setWay(mapArray, startx, starty - 1, end)) {// 向左
                    return true;
                } else {
                    mapArray[startx][starty] = 3;//说明该点是走不通的
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}
