package org.example.jucdemo2.volatiled;

import lombok.Data;
import org.junit.jupiter.api.Test;

public class VolatileTest {

    /*public static void main(String[] args) throws InterruptedException {
        RunThread myThread = new RunThread();
        myThread.start();
        Thread.sleep(3000);
        myThread.setRunning(false);
        System.out.println("isRunning 的值已经设置为了 false");
        Thread.sleep(1000);
        System.out.println(myThread.isRunning);
    }*/

    @Test
    void test() throws InterruptedException {

        Person person = new Person();

        new Thread(() -> {
            int i = 0;
            while (person.flag) {
                System.out.println("person flag： " + (i++));
            }

            System.out.println("a end");
        },"a").start();



        Thread.sleep(5);
        new Thread(() -> {
            person.flag = false;
            System.out.println("ddddddddddddddd");
        },"b").start();

    }
}

@Data
class Person {


    public boolean flag = true;
    //public volatile int number = 45;

}




















