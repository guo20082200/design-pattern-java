package com.zishi.algorithm.stack.calculator;

import org.junit.Test;

public class CalTest {

    @Test
    public void test02() {
        String experssion = "7+2*6-2";
        //创建两个栈
        ArrayStack2 numberStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        int index = 0;//用于扫描
        int num1 = 0, num2 = 0;
        int oper = 0;
        int res = 0;
        char ch = ' ';//将每次扫描得到的char放入
        //开始循环
        while (true) {
            //依次得到每一个字符
            ch = experssion.substring(index, index + 1).charAt(0);
            //判断ch是什么，然后做相应的处理
            if (operStack.isOper(ch)) {//如果是符号
                if (!operStack.isEmpty()) {//如果符号栈不为空
                    //如果是符号，进行比较，如果当前操作符的优先级小于或等于栈中运算符
                    if (operStack.priority(ch) <= operStack.priority(operStack.pick())) {
                        num1 = numberStack.pop();
                        num2 = numberStack.pop();
                        oper = operStack.pop();
                        res = numberStack.cal(num1, num2, oper);
                        numberStack.push(res);
                        operStack.push(ch);
                    } else {
                        operStack.push(ch);
                    }
                } else {
                    //如果为空直接入栈
                    operStack.push(ch);//如果

                }

            } else {//如果是数，则直接入栈
                numberStack.push(ch - 48);//ASCII码转换为数字
            }
            //index+1.是否扫描到expersion最后
            index++;
            if (index >= experssion.length()) {
                break;
            }
        }
        while (true) {
            //如果符号栈为空，则结算结束，数栈中只有一个数字
            if (operStack.isEmpty()) {
                break;
            }
            num1 = numberStack.pop();
            num2 = numberStack.pop();
            oper = operStack.pop();
            res = numberStack.cal(num1, num2, oper);
            numberStack.push(res);
        }
        //将数栈中最后一个打印出来
        System.out.println("表达式为：" + experssion + "结果为：" + numberStack.pop());

    }
}
