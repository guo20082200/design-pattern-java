package com.zishi.algorithm;

public class CircleLinkedListDemo {
    public static void main(String[] args) {

    }
}

//定义SingleLinkedList 管理我们的Hero
class CircleLinkedList {
    //创建一个first节点，当前没有编号
    private Node<Integer> first = new Node<Integer>(-1);

    public void add(int no) {
        //做一个数据校验
        if (no < 1) {
            System.out.println("数据输入错误");
            return;
        }
        //创建环形列表
        Node<Integer> cur = new Node<Integer>();
        for (int i = 1; i < no + 1; i++) {
            //根据编号创建节点
            Node<Integer> node = new Node<Integer>(i);
            if (i == 1) {
                first = node;
                node.setNext(first);  // 构成环形
                cur = first;
            } else {
                cur.setNext(node);
                node.setNext(first);
                cur = node;
            }

        }
    }


    //更新节点数据
    public void update(Node<Integer> node) {

    }


    //删除节点
    // 找到待删除节点的前一个节点
    public void delete(int no) {

    }

    //显示链表【遍历】
    //遍历当前环形列表
    public void show() {
        if (first == null) {
            System.out.println("链表为空");
            return;
        }
        Node<Integer> cur = new Node<>();
        cur = first;
        while (true) {
            System.out.print(cur.getData() + " ");
            if (cur.getNext() == first) {
                break;
            }
            cur = cur.getNext();
        }
    }
}

//定义Node ， 每个HeroNode对象就是一个节点
class Node<T> {
    public T data;
    public Node<T> next; //指向下一个节点

    public Node() {
    }

    //构造器
    public Node(T data) {
        this.data = data;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }


    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
