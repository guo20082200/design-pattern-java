package com.zishi.pattern.creational.prototype;

import java.io.*;
import java.util.Date;

/**
 * 深度克隆：第一种实现，
 * 原型类：被克隆的类型
 * 深度克隆测试
 *
 */
public class User2 implements Cloneable,Serializable{
	
	private String name;
	
	private Date birth;
	
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	/**
	 * 实现克隆的方法
	 * 深度克隆(deep clone)
	 */
	public Object clone() throws CloneNotSupportedException{
		Object object = super.clone();
		// 实现深度克隆(deep clone)
		User2 user = (User2)object;
		user.birth = (Date) this.birth.clone();
		return object;
	}

	/**
	 * 我们发现克隆的对象的属性并没有随着我们对Date的修改而改变，
	 * 说明克隆对象的Date属性和原型对象的Date属性引用的不是同一个对象，实现的深度复制。
	 *
	 * @param args
	 * @throws CloneNotSupportedException
	 */
	public static void main02(String[] args) throws CloneNotSupportedException {
		Date date =  new Date(1231231231231L);
		User2 user = new User2();
		user.setName("波波烤鸭");
		user.setAge(18);
		user.setBirth(date);
		System.out.println("----输出原型对象的属性------");
		System.out.println(user);
		System.out.println(user.getName());
		System.out.println(user.getBirth());
		// 克隆对象
		User2 user1 =(User2) user.clone();
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

	/**
	 * 深度克隆：第二种实现，序列化和反序列化
	 *
	 * @param args
	 * @throws CloneNotSupportedException
	 * @throws Exception
	 */
	public static void main(String[] args) throws CloneNotSupportedException, Exception {
		Date date =  new Date(1231231231231L);
		User user = new User();
		user.setName("波波烤鸭");
		user.setAge(18);
		user.setBirth(date);
		System.out.println("-----原型对象的属性------");
		System.out.println(user);
		System.out.println(user.getName());
		System.out.println(user.getBirth());

		//使用序列化和反序列化实现深复制
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(user);
		byte[] bytes = bos.toByteArray();

		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bis);

		//克隆好的对象！
		User user1 = (User) ois.readObject();

		// 修改原型对象的值
		date.setTime(221321321321321L);
		System.out.println(user.getBirth());

		System.out.println("------克隆对象的属性-------");
		System.out.println(user1);
		System.out.println(user1.getName());
		System.out.println(user1.getBirth());
	}
}