package org.example.jucdemo2.future02;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CompletableFutureDemoTest {

    final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Test
    void testConstructor() throws ExecutionException, InterruptedException {


        //1. 4个静态方法 返回 CompletableFuture

        //1.1 public static CompletableFuture<Void> runAsync(Runnable runnable)
        CompletableFuture<Void> future01 = CompletableFuture.runAsync(() -> System.out.println("hello"));

        Void unused01 = future01.get();
        //1.2 public static CompletableFuture<Void> runAsync(Runnable runnable,Executor executor)
        CompletableFuture<Void> future02 = CompletableFuture.runAsync(() -> System.out.println("hello"), executorService);
        Void unused02 = future02.get();

        //1.3 public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "xxxxx");
        String res = stringCompletableFuture.get();
        System.out.println(res);
        //1.4 public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor)
        CompletableFuture<String> stringCompletableFuture04 = CompletableFuture.supplyAsync(() -> "xxxxx04", executorService);
        String res04 = stringCompletableFuture04.get();
        System.out.println(res04);

    }

    @Test
    void testWhenComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(".........." + Thread.currentThread().getName());
            int a = 0;
           // System.out.println(2 / a);
            return "ExecutionException04";
        }, executorService);

        completableFuture.whenComplete((s, e) -> {
            if(e == null) {
                System.out.println(s);
            }
        });

        completableFuture.whenCompleteAsync((s, e) -> {

        });

        completableFuture.whenCompleteAsync((s, e) -> {

        }, executorService);

        completableFuture.exceptionally(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) {
                return "";
            }
        });

        completableFuture.exceptionallyAsync(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) {
                return "";
            }
        });

        completableFuture.exceptionallyAsync(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) {
                return "";
            }
        }, executorService);

        /*CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(".........." + Thread.currentThread().getName());
            int a = 0;
            // System.out.println(2 / a);
            return "ExecutionException04";
        }, executorService).whenComplete((s, e) -> {
            if(e == null) {
                System.out.println(s);
            }
        }).exceptionally()*/
    }

    @Test
    void testWhenComplete02() throws ExecutionException, InterruptedException {

        // supplyAsync 和 whenComplete 和 exceptionally 使用的同一个线程
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync.........." + Thread.currentThread().getName());
            int a = 0;
            //System.out.println(2 / a);
            return "ExecutionException04";
        }, executorService).whenComplete((s, e) -> {
            if(e == null) {
                System.out.println("whenComplete.........." + Thread.currentThread().getName());
                System.out.println(s);
            }
        }).exceptionally(throwable -> {
            System.out.println("exceptionally.........." + Thread.currentThread().getName());
            return "exceptionally";
        });


        System.out.println(".............................");
        CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync.........." + Thread.currentThread().getName());
            int a = 0;
            //System.out.println(2 / a);
            return "ExecutionException04";
        }, executorService).whenCompleteAsync((s, e) -> {
            if(e == null) {
                System.out.println("whenComplete.........." + Thread.currentThread().getName());
                System.out.println(s);
            }
        }).exceptionally(throwable -> {
            System.out.println("exceptionally.........." + Thread.currentThread().getName());
            return "exceptionally";
        });

    }


    @Test
    void testWhenComplete03() throws ExecutionException, InterruptedException {

        // supplyAsync 和 whenCompleteAsync 使用的不是一个线程, 如果没有指定线程池，使用默认的线程池
        // supplyAsync 和 exceptionallyAsync 使用的不是一个线程, 如果没有指定线程池，使用默认的线程池
        CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync.........." + Thread.currentThread().getName());
            int a = 0;
            System.out.println(2 / a);
            return "ExecutionException04";
        }, executorService).whenCompleteAsync((s, e) -> {
            if(e == null) {
                System.out.println("whenComplete.........." + Thread.currentThread().getName());
                System.out.println(s);
            }
        }, executorService).exceptionallyAsync(throwable -> {
            System.out.println("exceptionally.........." + Thread.currentThread().getName());
            return "exceptionally";
        }, executorService);

    }




}
