package org.example.jucdemo2.future02;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Book {

    private String name;
    private String author;

    public double getBookPrice(String name) {

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + name.charAt(0);
    }


}
