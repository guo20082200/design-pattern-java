package org.example.jucdemo2.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemoTest {

    public static void main(String[] args) {

        AtomicReference<User> atomicReference = new AtomicReference<>();

        User user = new User("za",18);
        User user02 = new User("zeeea",23);

        atomicReference.set(user);

        System.out.println(atomicReference.compareAndSet(user, user02) + "...." + atomicReference.get());
        System.out.println(atomicReference.compareAndSet(user, user02) + "...." + atomicReference.get());

    }
}
