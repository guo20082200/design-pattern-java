package org.example.jucdemo2.guava;

import com.google.common.util.concurrent.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class FutureDemo01 {

    static ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    /**
     * 提交任务
     */
    @Test
    void test01() {
        //synchronized ()
        ListenableFuture<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                // 模拟一些长时间的操作
                Thread.sleep(2000);
                return "Hello, Guava!";
            }
        });

        System.out.println(future.getClass());

    }

    /**
     * 添加回调
     */
    @Test
    void test02() {
        ListenableFuture<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                // 模拟一些长时间的操作
                Thread.sleep(2000);
                System.out.println(2 / 0);
                return "Hello, Guava!";
            }
        });

        // 用Futures.addCallback给future添加了一个回调。
        // 这样，一旦异步任务完成，无论是成功还是失败，相应的方法都会被调用。这样的处理方式既简洁又高效。
        Futures.addCallback(future, new FutureCallback<>() {

            @Override
            public void onSuccess(String result) {
                System.out.println(".................." + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(">>>>>>>>>>>>>>>>>>" + t.getMessage());
            }
        }, executorService);

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();

    }

    /**
     * 使用Futures.catching处理异常
     */
    @Test
    void test03() {
        ListenableFuture<String> future = executorService.submit(() -> {
            if (new Random().nextBoolean()) {
                throw new IllegalStateException("糟糕，出错了！");
            }
            return "一切正常";
        });

        // 使用Futures.catching处理异常
        ListenableFuture<String> catchingFuture = Futures.catching(future, IllegalStateException.class,
                (Exception e) -> "出现异常，但被处理了！", executorService);

        Futures.addCallback(catchingFuture, new FutureCallback<>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("结果：" + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.err.println("未捕获的异常：" + t.getMessage());
            }
        }, executorService);
    }

    //组合多个异步任务
    @Test
    void test04() {
        ListenableFuture<String> future1 = executorService.submit(() -> {
            // 模拟异步操作
            Thread.sleep(1000);
            return "任务1完成";
        });

        ListenableFuture<String> future2 = executorService.submit(() -> {
            // 模拟另一个异步操作
            Thread.sleep(1500);
            return "任务2完成";
        });

        // 将两个Future组合成一个
        ListenableFuture<List<String>> allFutures = Futures.allAsList(future1, future2);

        Futures.addCallback(allFutures, new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                // 当所有任务都成功完成后，这里会被调用
                result.forEach(System.out::println);
            }

            @Override
            public void onFailure(Throwable thrown) {
                // 如果任一任务失败，这里会被调用
                thrown.printStackTrace();
            }
        }, executorService);
    }



    /**
     * 链式调用异步任务
     *     另一个常见的需求是链式调用异步任务。比如，第一个任务完成后，它的输出将作为第二个任务的输入。
     *     Guava的Futures.transform方法在这里又派上用场了。
     */
    @Test
    void test05() {
        ListenableFuture<String> initialFuture = executorService.submit(() -> {
            // 模拟一个耗时的异步操作
            Thread.sleep(1000);
            return "初步处理完成";
        });

        Function<String, ListenableFuture<String>> followUpTask = (input) -> executorService.submit(() -> {
            // 使用前一个任务的结果
            return input + "，然后进行进一步处理";
        });

        // 将初始任务和后续任务链接起来
        //ListenableFuture<String> chainedFuture = Futures.transformAsync(initialFuture, followUpTask, executorService);

        Futures.addCallback(initialFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("链式任务的结果：" + result);
            }

            @Override
            public void onFailure(Throwable thrown) {
                thrown.printStackTrace();
            }
        }, executorService);

    }

}





