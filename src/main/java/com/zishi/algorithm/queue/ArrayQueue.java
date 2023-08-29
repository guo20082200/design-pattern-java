package com.zishi.algorithm.queue;

/**
 * @description: 使数组模拟队列类
 * @author: zishi
 */
public class ArrayQueue {
    private final int maxSize;//表示数组队列的最大容量
    private int front;//数组队列头部
    private int rear;//数组队列尾部
    private final int[] arr;//用于存放数据，模拟队列

    //创建队列的构造函数
    public ArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = -1;//指向队列头部，front是指向队列头部数据的前一个位置
        rear = -1;//指向队列尾部，rear是指向队列尾部数据
    }

    //判断队列是否已满
    public boolean isFull() {
        return rear == maxSize - 1;//rear队列尾部数据==最大容量，说明队列已满
    }

    //判断队列是否为空
    public boolean isEmpty() {
        return front == rear; //队列头部指针==队列尾部指针，说明队列为空
    }

    //添加数据到队列
    public void addQueue(int n) {
        //判断队列是否已满
        if (isFull()) {
            System.out.println("队列已满，不能往队列中添加数据。");
            return;
        }
        rear++;//队列尾部指针向后移动
        arr[rear] = n;
    }

    //获取队列数据，出队列
    public int getQueue() {
        //判断队列是否为空
        if (isEmpty()) {
            throw new RuntimeException("队列为空，不能从队列获取数据。");
        }
        front++;//队列头部指针向后移动
        return arr[front];
    }

    //显示队列中所有数据
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列是空的，没有数据。");
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    //显示队列的头部数据
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列是空的，没有数据。");
        }
        return arr[front + 1];
    }

}

