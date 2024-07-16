package com.zishi.react02;

import com.google.common.util.concurrent.*;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 众所周知，Reactive Programming是一种Pull-Push模型，其中Pull用于实现back-pressure，
 * <p>
 * Iterator 属于推模式（push-based），Reactive Flux/Mono 属于拉模式（pull-based）
 */
public class Demo01 {

    @Test
    void testIterator() {
        //创建一个迭代器
        Iterator<Integer> it = Arrays.asList(1, 2, 3).iterator();

        //使用迭代器
        while (it.hasNext()) {
            //模拟业务逻辑 —— 这里直接打印value
            System.out.println(it.next());
        }

    }


    /**
     * 将迭代器转换为flux
     *
     * @throws InterruptedException
     */
    @Test
    void fluxExample() throws InterruptedException {
        //创建迭代器
        Iterator<Integer> it = Arrays.asList(1, 2, 3).iterator();

        Flux<Integer> iteratorFlux = Flux.create(sink -> {
            while (it.hasNext()) {
                Integer data = it.next();
                sink.next(data); //利用FluxSink实现data的Push
            }
            sink.complete();  //发送结束的Signal
        });

        //进行订阅，进行业务逻辑操作
        iteratorFlux.log().subscribe(System.out::println);
    }


    /**
     * MonoCreate常见的两者使用方式
     * 传统命令式编程除了Iterator的Pull模式外，
     * 通常还有Observable以及Callback这两种Push模式，下面分别举例讲讲这两种模式。
     * <p>
     * Observable -> MonoCreate
     */
    @Test
    void testObservable() {
        Observable observable = new Observable() {
            //需要重写Observable，默认是setChanged与notifyObservers分离，实现先提交再通知的效果
            //这里为了简单起见，将通知与提交放在了一起
            @Override
            public void notifyObservers(Object arg) {
                setChanged();
                super.notifyObservers(arg);
            }
        };
        Observer first = (ob, value) -> {
            System.out.println("value is " + value);
        };
        observable.addObserver(first);
        for (int i = 0; i < 10; i++) {
            observable.notifyObservers("xxxxx " + i);
        }
        // after some time, cancel observer to dispose resource
        System.out.println("...........................");
        observable.deleteObserver(first);
    }

    /**
     * Observable -> MonoCreate
     * MonoCreate的转化示例：
     */
    @Test
    void testMonoCreate() {

        Observable observable = new Observable() {
            //需要重写Observable，默认是setChanged与notifyObservers分离，实现先提交再通知的效果
            //这里为了简单起见，将通知与提交放在了一起
            @Override
            public void notifyObservers(Object arg) {
                setChanged();
                super.notifyObservers(arg);
            }
        };

        Mono<Object> observableMono = Mono.create(sink -> {
            Observer first = (ob, value) -> {
                sink.success(value);
            };
            observable.addObserver(first);
            observable.notifyObservers("42"); // 这里只能写一次
            //observable.notifyObservers("42"); // 这次不起作用
            sink.onDispose(() -> observable.deleteObserver(first));
        });
        observableMono.subscribe(v -> System.out.println("value is " + v));

        Flux<Object> objectFlux = Flux.create(sink -> {
            Observer first = (ob, value) -> {
                sink.next(value);
            };
            observable.addObserver(first);
            for (int i = 0; i < 10; i++) {
                observable.notifyObservers("xxere:" + i);
            }
            sink.onDispose(() -> observable.deleteObserver(first));
        });

        objectFlux.subscribe(v -> System.out.println("value is " + v));

    }


    //============== Callback -> MonoCreate ===========================


    @Test
    void testCallback() throws InterruptedException {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(executorService);
        ListenableFuture future = service.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("on carry");
                throw new RuntimeException("....................");
                //System.out.println("mydebug   run, " + Thread.currentThread().getName());
            }
        });

        Futures.addCallback(future, new FutureCallback() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("mydebug   onSuccess, " + Thread.currentThread().getName());
            }

            @Override
            public void onFailure(Throwable thrown) {
                System.out.println("mydebug   onFailure, " + Thread.currentThread().getName() + "..." + thrown.getMessage());
            }
        }, executorService);
        Thread.sleep(1000);
    }


    static class CallbackHandlerInner {
        private MonoSink<Object> monoSink;
        private FutureCallback<Object> responseCallback;

        public MonoSink<Object> getMonoSink() {
            return monoSink;
        }

        public void setMonoSink(MonoSink<Object> monoSink) {
            this.monoSink = monoSink;
        }

        public FutureCallback<Object> getResponseCallback() {
            return responseCallback;
        }

        public void setResponseCallback(FutureCallback<Object> responseCallback) {
            this.responseCallback = responseCallback;
        }

        public CallbackHandlerInner(MonoSink<Object> monoSink) {
            this.monoSink = monoSink;
            responseCallback = new FutureCallback<>() {
                @Override
                public void onSuccess(@Nullable Object o) {
                    System.out.println(" .... 2: FutureCallback   run, " + Thread.currentThread().getName());
                    monoSink.success(o);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    monoSink.error(throwable);
                }

            };
        }
    }

    ExecutorService executorService = Executors.newFixedThreadPool(10);
    ExecutorService dbPool = Executors.newFixedThreadPool(10);

    @Test
    public void MonoCreateExample() throws InterruptedException {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(executorService);
        ListenableFuture<Object> future = service.submit(new Callable<Object>() {
            @Override
            public Object call() {
                System.out.println(" .... 1: mydebug   run, " + Thread.currentThread().getName());
                return "async out";
            }
        });

        Mono<Object> responseMono = Mono.create(monoSink -> {
            // callback的处理类，并传入monoSink供使用
            CallbackHandlerInner callbackHandler = new CallbackHandlerInner(monoSink);

            Futures.addCallback(future, callbackHandler.getResponseCallback(), dbPool);

        });
        responseMono.subscribeOn(Schedulers.single()).subscribe(out -> System.out.println(" .... 3: final out:, " + out.toString()));

        Thread.sleep(1000);
    }


}
