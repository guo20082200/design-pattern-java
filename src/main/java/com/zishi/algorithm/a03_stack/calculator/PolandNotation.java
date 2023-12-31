package com.zishi.algorithm.a03_stack.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    public static void main(String[] args) {
        //定义一个逆波兰表达式
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        //为了方便，逆波兰表达式的数字和空格隔开
        //思路
        //先将"3 4 + 5 * 6 -" 放入ArrayList中
        //4*5-8+60+8/2
        //4 5 * 8 - 60 + 8 2 / +
        //将ArrayList传递给一个方法，这个方法使用配合栈完成计算
        List<String> rpnList = getListString(suffixExpression);
        System.out.println("rpnList " + rpnList);
        int res = calculate(rpnList);
        System.out.println("计算结果为：" + res);

    }

    //将一个逆波兰表达式，依次将数据和运算符放入ArrayList中
    public static List<String> getListString(String suffixExperssion) {
        //将suffixExperssion分割
        String[] split = suffixExperssion.split(" ");//把字符串分隔开
        List<String> list = new ArrayList<String>();//创建一个新的序列
        for (String ele : split) {
            list.add(ele);
        }
        return list;
    }

    //完成运算
    public static int calculate(List<String> ls) {
        //创建栈，只需要一个栈即可
        Stack<String> stack = new Stack<String>();
        //遍历list
        for (String item : ls) {
            //使用正则表达式取出数
            if (item.matches("\\d+")) {//匹配的为多位数
                //入栈
                stack.push(item);
            } else {
                //pop出两个数并且运算，在入栈
                int num2 = Integer.parseInt(stack.pop());//后弹出-先弹出
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误");
                }
                stack.push(String.valueOf(res));
            }
        }
        return Integer.parseInt(stack.pop());
    }

    //将中缀表达式转换成对应的List
    public static List<String> toInfixExperssionList(String s) {
        List<String> ls = new ArrayList<String>();
        int i = 0;//这是一个指针，用于遍历中缀表达式字符串
        StringBuilder str;//多位数字的拼接
        char c;//每遍历到一个字符，就放入c
        do {
            //如果c是一个非数字，就需要加入ls
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {//不是数
                ls.add(String.valueOf(c));
                i++;
            } else {//如果是一个数们需要考虑多位数问题
                str = new StringBuilder();//清空
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {
                    str.append(c);
                    i++;
                }
                ls.add(str.toString());
            }

        } while (i < s.length());
        return ls;

    }

    //将得到的中缀表达式对应的list 转换为后缀表达式对应的list
    public static List<String> parseSuffixExpression(List<String> ls) {
        //定义两个栈
        Stack<String> s1 = new Stack<String>();
        //因为s2在整个转换过程中没有pop操作，而且还要逆序输出，因此直接使用List
        List<String> s2 = new ArrayList<String>();
        //遍历ls
        for (String item : ls) {
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());//s1一次弹出到s2
                }
                s1.pop();//将（弹出s1 消除(
            } else {
                //当item运算符的优先级小于s1栈顶运算符的优先级时，s1栈顶的运算符弹出栈，压入到s2
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    s2.add(s1.pop());
                }
                //把当前item运算符，压入栈中
                s1.push(item);
            }
        }
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        return s2;//顺序输出即可
    }
}