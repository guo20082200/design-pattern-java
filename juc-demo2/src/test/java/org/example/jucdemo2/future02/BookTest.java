package org.example.jucdemo2.future02;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    static List<Book> books = new ArrayList<>();
    @BeforeAll
    static void setUp() {
        books.add(new Book("mysql1", "zhangsan"));
        books.add(new Book("mysql2", "zhangsan"));
        books.add(new Book("mysql3", "zhangsan"));
        books.add(new Book("mysql4", "zhangsan"));
        books.add(new Book("mysql5", "zhangsan"));
    }

    /**
     * 测试 CompletableFuture 方法
     */
    @Test
    void testCompletableFuture() {

        long start = System.currentTimeMillis();

        List<String> list = books.stream()
                .map(x -> CompletableFuture.supplyAsync(
                        () -> String.format("%s  xxx %s, ... %.2f", x.getName(), x.getAuthor(), x.getBookPrice(x.getName()))
                        )
                )
                .toList()
                .stream()
                .map(CompletableFuture::join)
                .toList();
        System.out.println(list);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

}