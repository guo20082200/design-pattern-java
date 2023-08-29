package com.zishi.algorithm.queue;

import java.util.Stack;

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        //测试
        HeroNode heroNode1 = new HeroNode(1, "11", "111");
        HeroNode heroNode2 = new HeroNode(2, "22", "222");
        HeroNode heroNode3 = new HeroNode(3, "33", "333");
        HeroNode heroNode4 = new HeroNode(4, "44", "444");
        //创建一个链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        //add数据
        singleLinkedList.add(heroNode1);
        singleLinkedList.add(heroNode2);
        singleLinkedList.add(heroNode3);
        singleLinkedList.add(heroNode4);
        //显示链表
        singleLinkedList.showLinkedList();
    }

    // 单链表元素的个数
    public static int getLength(HeroNode head) {

        if (head.next == null) {// 空链表
            return 0;
        }
        int length = 0;
        // 定义一个辅助变量,这里我们没有统计头结点
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next;// 后移，实现遍历
        }
        return length;
    }


    // 查找单链表中倒数第index个节点：
    public static HeroNode findLastIndexNode(HeroNode head, int index) {

        if (head.next == null) {// 链表为空
            return null;// 没有找到
        }
        // 第一次遍历得到链表的长度
        int size = getLength(head);
        // 第二次遍历到size-index位置上，就是我们倒数第K个节点
        // 先做一个index的校验
        if (index < 0 || index > size) {
            return null;
        }
        // 定义一个辅助变量,for循环定位到倒数的index
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }


    /**
     * 单链表的反转
     * 思路：
     * 1.首先我们需要判断需要反转的链表是否为空或者是不是只有一个节点，如果是的话就直接return就好了，因为反转之后的链表和反转之前的链表是一样的。
     * 2.如果需要反转的链表不为空且不是只有一个节点的话，我们就需要创建另外一个链表(新链表的头结点为reverseHead)，因为只依靠一个链表是无法完成链表的反转操作的。
     * 3.遍历原先的链表，在遍历之前还需要定义两个辅助指针cur用于遍历，next用于保存当前节点的下一个节点的信息，每遍历到一个节点就把这个节点下一个节点指向reverseHead的next节点，再让reverseHead的next节点指向当前遍历到的这个节点，
     * 循环遍历结束后将原先这个链表的头结点head的next节点指向新链表头结点reverseHead的next的节点，这样就完成了链表的反转。
     *
     * @param head
     */
    public static void reverseList(HeroNode head) {

        // 如果当前的链表为空，或者只有一个节点则无需反转，直接返回
        if (head.next == null || head.next.next == null) {
            return;
        }
        // 定义一个辅助的指针(变量)，帮助我们遍历原来的链表
        HeroNode cur = head.next;
        HeroNode next = null;// 用于指向当前节点【cur节点】的下一个节点
        HeroNode reverseHead = new HeroNode(0, "", "");
        // 遍历原来的链表
        // 每遍历一个节点，就将其取出，并放在新链表reverseHead的最前端
        while (cur != null) {
            next = cur.next;// 先暂时保存当前节点的下一个节点，因为后面需要使用
            cur.next = reverseHead.next;// 将cur的下一个节点指向新链表的最前端
            reverseHead.next = cur;// 将cur连接到新链表上
            cur = next;// 让cur后移
        }
        // 将head.next指向reverseHead.next，实现单链表的反转
        head.next = reverseHead.next;
    }


    /**
     * 四、从尾到头打印单链表：
     * 思路：
     * 1.方式一：可以将这个单链表反转之后在打印出来，可以实现了逆序打印的效果，但是这种方式破坏了单链表，不可取
     * 2.方式二:使用栈这个数据结构，将各个节点压入到栈中，然后利用栈的先进后出的特点，就实现了逆序打印的效果
     */
    public static void reversePrint(HeroNode head) {
        if (head.next == null) {
            return;// 空链表不能打印
        }
        // 创建一个类型为HeroNode的栈
        Stack<HeroNode> stack = new Stack<HeroNode>();
        HeroNode cur = head.next;//定义一个辅助变量用于遍历链表
        // 将链表的所有元素压入栈中
        while (cur != null) {
            stack.push(cur);//将链表中的节点压入栈中
            cur = cur.next;// 让cur后移，这样就可以压入下一个节点
        }
        // 循环遍历栈中节点元素将栈中的节点元素进行打印
        while (stack.size() > 0) {
            System.out.println(stack.pop());// 将栈中的节点元素出栈
        }
    }


}

//定义SingleLinkedList 管理我们的Hero
class SingleLinkedList {
    //先初始化一个头节点！头节点不要动，不存放具体的数据。
    private HeroNode head = new HeroNode(0, "", "");

    //添加节点到单向链表
    //当不考虑编号顺序时
    //1.找到当前链表的最后节点
    //2.将最后这个节点的next指向新的节点
    public void add(HeroNode heroNode) {

        //因为head节点不能动，因此我们需要一个辅助变量 temp
        HeroNode temp = head;
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
    }


    //添加指定位置
    public void addHeroByOrder(HeroNode heroNode) {
        boolean flag = false;
        HeroNode temp = head;
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
        }

    }


    //更新节点数据
    public void update(HeroNode updateHero) {
        HeroNode temp = head;
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
    // 找到待删除节点的前一个节点
    public void delete(int no) {
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.next = temp.next.next;
        } else {
            System.out.printf("要删除的编号%d节点不存在", no);
        }
    }


    //显示链表【遍历】
    public void showLinkedList() {
        //判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空！");
            return;
        }
        //因为头节点不能动，因此我们需要一个辅助变量来遍历
        HeroNode temp = head.next;
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
}

//定义HeroNode ， 每个HeroNode对象就是一个节点
class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next; //指向下一个节点

    //构造器
    public HeroNode(int no, String name, String nickname) {
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
