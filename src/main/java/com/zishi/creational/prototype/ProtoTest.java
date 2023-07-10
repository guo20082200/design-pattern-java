package com.zishi.creational.prototype;

import java.util.Date;

/**
 * 浅克隆的问题:虽然产生了两个完全不同的对象，但是被复制的对象的所有变量都含有与原来的对象相同的值，而所有的对其他对象的引用都仍然指向原来的对象。
 */
public class ProtoTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        Date date = new Date(1231231231231L);
        User user = new User();
        System.out.println(user.hashCode());
        user.setName("波波烤鸭");
        user.setAge(18);
        user.setBirth(date);
        System.out.println("----输出原型对象的属性------");
        System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getBirth());
        // 克隆对象
        User user1 = (User) user.clone();
        System.out.println(user1.hashCode());
        // 修改原型对象中的属性
        date.setTime(123231231231L);
        System.out.println(user.getBirth());

        // 修改参数
        user1.setName("dpb");
        System.out.println("-------克隆对象的属性-----");
        System.out.println(user1);
        System.out.println(user1.getName());
        System.out.println(user1.getBirth());
    }

}
