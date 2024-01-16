package com.zishi.algorithm.a02_queue;

public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        //测试
        HeroNode2 heroNode1 = new HeroNode2(1, "11", "111");
        HeroNode2 heroNode2 = new HeroNode2(2, "22", "222");
        HeroNode2 heroNode3 = new HeroNode2(3, "33", "333");
        HeroNode2 heroNode4 = new HeroNode2(4, "44", "444");
        HeroNode2 heroNode5 = new HeroNode2(2, "22222", "22222222");
        //创建一个链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();

        //add数据
        doubleLinkedList.addHeroByOrder(heroNode1);
        doubleLinkedList.addHeroByOrder(heroNode4);
        doubleLinkedList.addHeroByOrder(heroNode3);
        doubleLinkedList.addHeroByOrder(heroNode2);

        //显示链表
        doubleLinkedList.showLinkedList();

        doubleLinkedList.update(heroNode5);
//        //delete数据
//        doubleLinkedList.delete(3);
        System.out.println("================================");
        System.out.println(heroNode4.pre.pre);
    }
}

class DoubleLinkedList {
    //初始化头节点
    private HeroNode2 head = new HeroNode2(0, "", "");

    //返回头节点
    public HeroNode2 getHead() {
        return head;
    }

    //遍历双向链表
    //显示链表【遍历】
    public void showLinkedList() {
        //判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空！");
            return;
        }
        //因为头节点不能动，因此我们需要一个辅助变量来遍历
        HeroNode2 temp = head.next;
        while (true) {
            //判断是否到链表最后
            if (temp == null) {
                break;
            }
            //输出节点信息
            System.out.println(temp);
            //将next后移，不后移就是个死循环
            temp = temp.next;
        }
    }

    //添加节点到双向链表
    //当不考虑编号顺序时
    //1.找到当前链表的最后节点
    //2.将最后这个节点的next指向新的节点
    public void add(HeroNode2 heroNode) {

        //因为head节点不能动，因此我们需要一个辅助变量 temp
        HeroNode2 temp = head;
        //遍历链表，找到最后
        while (true) {
            //当next == null时,找到链表最后
            if (temp.next == null) {
                break;
            }
            //如果没有找到最后,将temp后移一位
            temp = temp.next;
        }
        //当退出while循环时，temp就指向了链表的最后
        //将最后这个节点的next指向新的节点
        temp.next = heroNode;
        //形成双向链表
        heroNode.pre = temp;
    }

    //--------------------------------------------------------
    //添加指定位置 双向链表
    public void addHeroByOrder(HeroNode2 heroNode) {
        boolean flag = false;
        HeroNode2 temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no > heroNode.no) {
                break;
            } else if (temp.next.no == heroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            System.out.printf("编号%d已经存在", heroNode.no);
        } else {
            heroNode.next = temp.next;
            temp.next = heroNode;
            heroNode.pre = temp;
            //判断下一个节点是否有没有数据，如果有数据就把pre指向上一个节点，如果没有就跳过，如果不判断会报空指针异常。
            if (heroNode.next != null) {
                heroNode.next.pre = heroNode;
            }
        }
    }
//======================================================

    //更新节点数据 和单向链表一样。
    public void update(HeroNode2 updateHero) {
        HeroNode2 temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == updateHero.no) {
                //修改
                flag = true;
                break;
            }

            temp = temp.next;
        }
        if (flag) {
            temp.next.name = updateHero.name;
            temp.next.nickname = updateHero.nickname;
        } else {
            System.out.printf("没有找到%d编号的节点\n", updateHero.no);
        }
    }


    //删除节点
    public void delete(int no) {
        if (head.next == null) {
            System.out.println("链表为空！");
            return;
        }
        HeroNode2 temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.pre.next = temp.next;
            if (temp.next != null) { // 判断是否为最后一个节点
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.printf("要删除的编号%d节点不存在", no);
        }
    }
}


//定义HeroNode2 ， 每个HeroNode对象就是一个节点
class HeroNode2 {
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next; //指向下一个节点 默认为null
    public HeroNode2 pre; //指向上一个节点 默认为null

    //构造器
    public HeroNode2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    //重写ToString
    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
