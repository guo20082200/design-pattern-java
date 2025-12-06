package org.example.jucdemo2.abc;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PutIfAbsentAtomicityTest {

    private static final int NUM_THREADS = 200;
    private static final String TEST_KEY = "SingleKey";
    
    // 用于存储结果的并发Map
    private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    //private final HashMap<String, String> map = new HashMap<>();

    // 用于记录 putIfAbsent 返回 null (成功插入) 的次数
    private final AtomicInteger successCount = new AtomicInteger(0);

    public void runTest() throws InterruptedException {
        
        System.out.println("--- 启动 putIfAbsent 原子性测试 ---");
        System.out.println("线程数: " + NUM_THREADS);
        
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            final String threadValue = "Value-" + i;
            executor.execute(() -> {
                try {
                    // 等待所有线程准备就绪
                    startSignal.await(); 
                    
                    // 核心操作：所有线程尝试插入同一个 Key
                    String result = map.putIfAbsent(TEST_KEY, threadValue);

                    if (result == null) {
                        // 如果返回 null，表示当前线程是第一个成功插入的
                        successCount.incrementAndGet();
                        System.out.println("✅ Thread " + Thread.currentThread().getName() + " succeeded (Returned null). Inserted: " + threadValue);
                    } else {
                        // 如果返回非 null，表示插入失败，其他线程已捷足先登
                        System.out.println("❌ Thread " + Thread.currentThread().getName() + " failed (Returned existing value).");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneSignal.countDown();
                }
            });
        }

        // 3. 释放信号，让所有线程同时开始竞争
        startSignal.countDown();
        
        // 4. 等待所有线程执行完毕
        doneSignal.await();
        executor.shutdown();

        // 5. 检查结果
        System.out.println("\n--- 验证结果 ---");
        System.out.println("Map 中最终的 Key 数量: " + map.size());
        System.out.println("Map 中 " + TEST_KEY + " 对应的值: " + map.get(TEST_KEY));
        System.out.println("putIfAbsent 返回 null 的次数 (成功插入次数): " + successCount.get());

        // 6. 原子性结论
        if (map.size() == 1 && successCount.get() == 1) {
            System.out.println("\n🎉 结论: putIfAbsent 成功展示了**原子性**和**排他性**。");
        } else {
            System.err.println("\n🚨 结论: 验证失败。结果不符合原子操作的预期。");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new PutIfAbsentAtomicityTest().runTest();
    }
}