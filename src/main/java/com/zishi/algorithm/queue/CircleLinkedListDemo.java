package com.zishi.algorithm.queue;

public class CircleLinkedListDemo {


    public static int findWinner(int n, int k) {
        int pos = 0;
        for (int i = 2; i < n + 1; i++) {
            pos = (pos + k) % i;
        }

        return pos + 1;
    }

    public static void main(String[] args) {
        /*CircleLinkedList circleLinkedList = new CircleLinkedList();
        circleLinkedList.init(5);
        circleLinkedList.traversal();*/

        int winner = findWinner(5, 2);
        System.out.println(winner);
    }
}

//定义SingleLinkedList 管理我们的Hero
class CircleLinkedList {
    //创建一个first节点，当前没有编号
    private Node first = new Node(-1);

    // 约瑟夫森环问题
    public void josephus() {

    }

    // 初始化num个节点
    public void init(int num) {

        //做一个数据校验
        if (num < 1) {
            System.out.println("数据输入错误");
            return;
        }

        //创建环形列表
        Node cur = new Node();
        for (int i = 1; i < num + 1; i++) {
            //根据编号创建节点
            Node node = new Node(i);
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

    // 添加元素，追加到最后
    public void add(int data) {
        //因为head节点不能动，因此我们需要一个辅助变量 temp
        Node temp = first;
        //遍历链表，找到最后
        while (true) {
            //当next == null时,找到链表最后
            if (temp.getNext() == first) {
                break;
            }
            //如果没有找到最后,将temp后移一位
            temp = temp.getNext();
        }
        //当退出while循环时，temp就指向了链表的最后一个节点
        //将最后这个节点的next指向新的节点
        Node addNode = new Node(data);
        temp.setNext(addNode); // 设置最后一个节点的下一个节点为待增加的节点
        addNode.setNext(first);// 设置待增加的节点的下一个节点为首节点
    }

    // 按照顺序添加元素
    public void addByOrder(Node node) {
        boolean flag = false;
        Node temp = first;
        //遍历链表，找到元素的位置
        while (true) {
            //当找到链表最后时
            if (temp.getNext() == first) {
                break;
            }
            if (temp.getNext().getData() > node.getData()) {
                break;
            } else if (temp.getNext().getData() == node.getData()) {
                flag = true;
                break;
            }
            temp = temp.getNext();

        }
        if (flag) {
            System.out.printf("编号%d已经存在", node.getData());
        } else {
            node.setNext(temp.getNext());// 设置待增加的节点的下一个节点为temp的下一个节点
            temp.setNext(node); // 设置temp的下一个节点为待增加的节点

        }
    }

    //更新节点数据
    public void update(Node node) {

    }

    //删除节点： 找到待删除节点的前一个节点
    public void delete(int no) {
        Node temp = first;
        boolean flag = false;
        //遍历链表，找到最后
        while (true) {
            //当next == null时,找到链表最后
            if (temp.getNext() == first) {
                break;
            }
            if (temp.getNext().getData() == no) {
                flag = true;
                break;
            }

            //如果没有找到最后,将temp后移一位
            temp = temp.getNext();
        }
        if (flag) {
            temp.setNext(temp.getNext().getNext());
        } else {
            System.out.printf("要删除的编号%d节点不存在", no);
        }
    }

    // 遍历循环链表
    public void traversal() {

        //判断链表是否为空,当first节点的下一个节点还是first，那么为空
        if (first.getNext() == first) {
            System.out.println("链表为空！");
            return;
        }
        //复制首节点引用
        Node temp = first;
        while (true) {
            //输出节点信息
            System.out.println(temp);

            //判断是否到链表最后
            if (temp.getNext() == first) {
                break;
            }
            //将next后移，不后移就是个死循环
            temp = temp.getNext();

        }
    }
}

//定义Node ， 每个Node对象就是一个节点
class Node {
    private int data;
    private Node next; //指向下一个节点

    public Node() {
    }

    //构造器
    public Node(int data) {
        this.data = data;
    }


    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
