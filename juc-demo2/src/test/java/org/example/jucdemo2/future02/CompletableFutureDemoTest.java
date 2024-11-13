package org.example.jucdemo2.future02;

import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.BiFunction;
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
    void testGetAndJoin() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "xxxxx");
        try {
            // get 抛出异常
            // 等待future结束然后获取结果
            System.out.println(completableFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        // join不跑出异常
        // 当任务完成的时候返回结果，或者如果任务异常结束，抛出一个非受查异常
        System.out.println(completableFuture.join());
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
            if (e == null) {
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
            if (e == null) {
                System.out.println("whenComplete.........." + Thread.currentThread().getName());
                System.out.println(s);
            }
        }).exceptionally(throwable -> {
            System.out.println("exceptionally.........." + Thread.currentThread().getName());
            return "exceptionally";
        });


        // supplyAsync 和 whenCompleteAsync 使用的不是一个线程, 使用默认的线程池
        CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync.........." + Thread.currentThread().getName());
            int a = 0;
            //System.out.println(2 / a);
            return "ExecutionException04";
        }, executorService).whenCompleteAsync((s, e) -> {
            if (e == null) {
                System.out.println("whenComplete.........." + Thread.currentThread().getName());
                System.out.println(s);
            }
        }).exceptionally(throwable -> {
            System.out.println("exceptionally.........." + Thread.currentThread().getName());
            return "exceptionally";
        });

        // supplyAsync 和 whenCompleteAsync 使用的不是一个线程, 如果没有指定线程池，使用默认的线程池
        // supplyAsync 和 exceptionallyAsync 使用的不是一个线程, 如果没有指定线程池，使用默认的线程池
        CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync.........." + Thread.currentThread().getName());
            int a = 0;
            System.out.println(2 / a);
            return "ExecutionException04";
        }, executorService).whenCompleteAsync((s, e) -> {
            if (e == null) {
                System.out.println("whenComplete.........." + Thread.currentThread().getName());
                System.out.println(s);
            }
        }, executorService).exceptionallyAsync(throwable -> {
            System.out.println("exceptionally.........." + Thread.currentThread().getName());
            return "exceptionally";
        }, executorService);

    }


    /**
     * 「get()和 get(long timeout, TimeUnit unit)」 => 在 Future 中就已经提供了，后者提供超时处理，如果在指定时间内未获取结果将抛出超时异常
     * 「getNow」 => 立即获取结果不阻塞，结果计算已完成将返回结果或计算过程中的异常，如果未计算完成将返回设定的 valueIfAbsent 值
     * 「join」 => 方法里不会抛出异常
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testCompletableGet() throws InterruptedException, ExecutionException {

        CompletableFuture<String> cp1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "商品A";
        });

        // getNow方法测试
        System.out.println(cp1.getNow("商品B"));

        //join方法测试
        CompletableFuture<Integer> cp2 = CompletableFuture.supplyAsync((() -> 1 / 2));
        System.out.println(cp2.join());
        System.out.println("-----------------------------------------------------");
        //get方法测试
        CompletableFuture<Integer> cp3 = CompletableFuture.supplyAsync((() -> 1 / 2));
        System.out.println(cp3.get());
    }

    @Test
    public void testThenRun() throws InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();

        CompletableFuture<Void> cp1 = CompletableFuture.runAsync(() -> {
            try {
                //执行任务A
                System.out.println("A");
                Thread.sleep(600);
                System.out.println("A end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        // 「做完第一个任务后，再做第二个任务,第二个任务也没有返回值」
        // 调用 thenRun 方法执行第二个任务时，则第二个任务和第一个任务是共用同一个线程池。
        CompletableFuture<Void> cp2 = cp1.thenRun(() -> {
            try {
                //执行任务B
                System.out.println("B");
                Thread.sleep(400);
                System.out.println("B end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 「做完第一个任务后，再做第二个任务,第二个任务也没有返回值」
        // 调用 thenRunAsync 执行第二个任务时，则第一个任务使用的是你自己传入的线程池，第二个任务使用的是 ForkJoin 线程池。
        CompletableFuture<Void> cp3 = cp1.thenRunAsync(() -> {
            try {
                //执行任务B
                System.out.println("C");
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        // get方法测试
        System.out.println(cp2.get());

        //模拟主程序耗时时间
        Thread.sleep(600);
        System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
    }


    /**
     * 第一个任务执行完成后，执行第二个回调方法任务，会将该任务的执行结果，作为入参，传递到回调方法中，但是回调方法是没有返回值的。
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCompletableThenAccept() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        CompletableFuture<String> cp1 = CompletableFuture.supplyAsync(() -> "dev");
        CompletableFuture<Void> cp2 = cp1.thenAccept((a) -> {
            System.out.println("上一个任务的返回结果为: " + a);
        });

        System.out.println(cp2.get());
    }


    /**
     * 表示第一个任务执行完成后，执行第二个回调方法任务，会将该任务的执行结果，作为入参，传递到回调方法中，并且回调方法是有返回值的。
     */
    @Test
    public void testCompletableThenApply() {
        CompletableFuture<String> cp1 = CompletableFuture.supplyAsync(() -> "dev").thenApply((a) -> Objects.equals(a, "dev") ? "dev" : "prod");

        System.out.println("当前环境为:" + cp1.join());

    }


    /**
     * thenCombine / thenAcceptBoth / runAfterBoth 都表示：「当任务一和任务二都完成再执行任务三」。
     * <p>
     * 区别在于：
     * <p>
     * 「runAfterBoth」 不会把执行结果当做方法入参，且没有返回值
     * 「thenAcceptBoth」: 会将两个任务的执行结果作为方法入参，传递到指定方法中，且无返回值
     * 「thenCombine」：会将两个任务的执行结果作为方法入参，传递到指定方法中，且有返回值
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCompletableThenCombine() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //开启异步任务1
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务1，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 1;
            System.out.println("异步任务1结束");
            return result;
        }, executorService);

        //开启异步任务2
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务2，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 1;
            System.out.println("异步任务2结束");
            return result;
        }, executorService);

        //任务组合 thenCombineAsync
        CompletableFuture<Integer> task3 = task.thenCombineAsync(task2, (f1, f2) -> {
            System.out.println("执行任务3，当前线程是：" + Thread.currentThread().getId());
            System.out.println("任务1返回值：" + f1);
            System.out.println("任务2返回值：" + f2);
            return f1 + f2;
        }, executorService);

        Integer res = task3.join();
        System.out.println("最终结果：" + res);


        CompletableFuture<Void> task4 = task.runAfterBothAsync(task2, () -> {
            System.out.println("执行任务4，当前线程是：" + Thread.currentThread().getId());
            System.out.println("任务1返回值：");
            System.out.println("任务2返回值：");
        }, executorService);

        System.out.println("task4最终结果：" + task4.join());

        CompletableFuture<Void> task5 = task.thenAcceptBothAsync(task2, (f1, f2) -> {
            System.out.println("执行任务5，当前线程是：" + Thread.currentThread().getId());
            System.out.println("任务1返回值：" + f1);
            System.out.println("任务2返回值：" + f2);
        }, executorService);
        System.out.println("task5最终结果：" + task5.join());

    }


    /**
     * applyToEither / acceptEither / runAfterEither 都表示：「两个任务，只要有一个任务完成，就执行任务三」。
     * <p>
     * 区别在于：
     * <p>
     * 「runAfterEither」：不会把执行结果当做方法入参，且没有返回值
     * 「acceptEither」: 会将已经执行完成的任务，作为方法入参，传递到指定方法中，且无返回值
     * 「applyToEither」：会将已经执行完成的任务，作为方法入参，传递到指定方法中，且有返回值
     */
    @Test
    public void testCompletableEitherAsync() throws InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //开启异步任务1
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务1，当前线程是：" + Thread.currentThread().getId());

            int result = 1 + 1;
            System.out.println("异步任务1结束");
            return result;
        }, executorService);

        //开启异步任务2
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务2，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 2;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务2结束");
            return result;
        }, executorService);

        //任务组合
        task.acceptEitherAsync(task2, (res) -> {
            System.out.println("执行任务3，当前线程是：" + Thread.currentThread().getId());
            System.out.println("上一个任务的结果为：" + res);
        }, executorService);

        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * allOf：等待所有任务完成
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCompletableAllOf() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //开启异步任务1
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务1，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 1;
            System.out.println("异步任务1结束");
            return result;
        }, executorService);

        //开启异步任务2
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务2，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 2;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务2结束");
            return result;
        }, executorService);

        //开启异步任务3
        CompletableFuture<Integer> task3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步任务3，当前线程是：" + Thread.currentThread().getId());
            int result = 1 + 3;
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务3结束");
            return result;
        }, executorService);

        //任务组合
        CompletableFuture<Void> allOf = CompletableFuture.allOf(task, task2, task3);

        //等待所有任务完成
        allOf.get();
        //获取任务的返回结果
        System.out.println("task结果为：" + task.get());
        System.out.println("task2结果为：" + task2.get());
        System.out.println("task3结果为：" + task3.get());
    }

    /**
     * anyOf: 只要有一个任务完成
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCompletableAnyOf() throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //开启异步任务1
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> 1 + 1, executorService);

        //开启异步任务2
        CompletableFuture<Integer> task2 = CompletableFuture.supplyAsync(() -> 1 + 2, executorService);

        //开启异步任务3
        CompletableFuture<Integer> task3 = CompletableFuture.supplyAsync(() -> 1 + 3, executorService);

        //任务组合
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(task, task2, task3);
        //只要有一个有任务完成
        Object o = anyOf.get();
        System.out.println("完成的任务的结果：" + o);
    }



    /**
     * thenCompose 和 exceptionallyCompose
     */
    @Test
    void testThenCompose()  {
        //创建线程池
        ExecutorService executorService222 = Executors.newFixedThreadPool(10);

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> 1 + 1, executorService222)
                .thenCompose(res -> CompletableFuture.supplyAsync(() -> {
                    int a = 0;
                    System.out.println(2 / a);
                    return res + 1 + "ssss";
                }, executorService222))
                .exceptionallyCompose(throwable -> {
                    System.out.println("exceptionallyCompose");
                    return CompletableFuture.supplyAsync(() -> throwable.getMessage() + "xxx", executorService);
                });
        System.out.println(completableFuture.join());

    }

    /**
     * handle 和  whenComplete 区别：
     * whenComplete：不改变结果类型 public CompletionStage<T> whenComplete (BiConsumer<? super T, ? super Throwable> action);
     * handle： 改变结果类型 public <U> CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn);
     *
     */
    @Test
    void testHandle()  {
        ExecutorService executorService222 = Executors.newFixedThreadPool(10);
        CompletableFuture<Integer> task = CompletableFuture.supplyAsync(() -> 1 + 1, executorService);

        task.handle(new BiFunction<Integer, Throwable, Object>() {
            @Override
            public Object apply(Integer integer, Throwable throwable) {
                return null;
            }
        });

    }


}

