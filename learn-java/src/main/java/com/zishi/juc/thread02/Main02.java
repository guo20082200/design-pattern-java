package com.zishi.juc.thread02;

// 多线程
public class Main02 {
    public static void main(String[] args) throws Exception {
        var ts = new Thread[]{new AddStudentThread(), new DecStudentThread(), new AddTeacherThread(), new DecTeacherThread()};
        for (var t : ts) {
            t.start();
        }
        for (var t : ts) {
            t.join();
        }
        System.out.println(Counter02.studentCount);
        System.out.println(Counter02.teacherCount);
    }
}

class Counter02 {
    public static final Object lock = new Object();
    public static int studentCount = 0;
    public static int teacherCount = 0;
}

class AddStudentThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (Counter.lock) {
                Counter02.studentCount += 1;
            }
        }
    }
}

class DecStudentThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (Counter.lock) {
                Counter02.studentCount -= 1;
            }
        }
    }
}

class AddTeacherThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (Counter.lock) {
                Counter02.teacherCount += 1;
            }
        }
    }
}

class DecTeacherThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (Counter.lock) {
                Counter02.teacherCount -= 1;
            }
        }
    }
}

/**
 * 上述代码的4个线程对两个共享变量分别进行读写操作，但是使用的锁都是Counter.lock这一个对象，
 * 这就造成了原本可以并发执行的Counter.studentCount += 1和Counter.teacherCount += 1，现在无法并发执行了，执行效率大大降低。
 * 实际上，需要同步的线程可以分成两组：AddStudentThread和DecStudentThread，AddTeacherThread和DecTeacherThread，组之间不存在竞争，
 * 因此，应该使用两个不同的锁，即：
 *
 * AddStudentThread和DecStudentThread使用lockStudent锁：
 */