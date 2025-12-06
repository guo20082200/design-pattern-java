package org.example.jucdemo2.abc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * put、putIfAbsent、compute、computeIfAbsent 和 computeIfPresent
 *
 * 使用 computeIfAbsent 配合 AtomicInteger 实现 ID 级别并发控制。
 * AtomicInteger 的状态: 0=空闲(Free), 1=正在处理(Processing)。
 */
public class AtomicIntegerConcurrencyControlService {

    // 存储每个用户ID对应的状态对象。
    // Key: 用户ID (String)
    // Value: AtomicInteger (用于表示和原子更新状态)
    private final ConcurrentHashMap<String, AtomicInteger> userOperations = new ConcurrentHashMap<>();

    // 模拟耗时服务
    private static final class UserService {
        private static final AtomicLong executionCount = new AtomicLong(0);

        public boolean saveUserInfo(String userId, String data) throws InterruptedException {
            long currentCount = executionCount.incrementAndGet();
            System.out.println(">>> [START] Thread: " + Thread.currentThread().getName() + 
                               ", UserId: " + userId + 
                               ", Execution: " + currentCount);
            
            Thread.sleep(2000); // 模拟耗时操作

            System.out.println("<<< [END] Thread: " + Thread.currentThread().getName() + 
                               ", UserId: " + userId + 
                               ", Execution: " + currentCount + 
                               " -> Data saved successfully.");
            return true;
        }
    }

    /**
     * 提交用户信息的同步方法。
     * * @param userId 用户的唯一ID
     * @param userData 用户提交的数据
     * @return true 如果保存操作开始执行；false 如果该ID的操作已经在进行中，本次提交被跳过。
     */
    public boolean submitUserInfo(String userId, String userData) {
        
        // 1. 原子获取或创建状态对象：
        // 如果 userId 不存在，则创建一个新的 AtomicInteger(0) 并放入 Map。
        // 如果 userId 存在，则返回已存在的 AtomicInteger 对象。
        AtomicInteger status = userOperations.computeIfAbsent(userId, k -> {
            System.out.println("--- [CREATE] New status object created for: " + userId);
            return new AtomicInteger(0);
        });
        
        // 2. 尝试抢占执行权：使用 compareAndSet (CAS)
        // 只有当当前值是 0 (空闲) 时，才将其原子性地设置为 1 (处理中)。
        boolean acquired = status.compareAndSet(0, 1);

        if (!acquired) {
            // 3. 抢占失败：说明状态已经是 1，有其他线程正在处理。
            System.out.println("--- [SKIP] UserId: " + userId + 
                               " is already processing (status=1). Current submission skipped.");
            return false;
        }

        try {
            // 4. 抢占成功：执行耗时的业务逻辑。
            System.out.println("--- [PROCESS] UserId: " + userId + 
                               " operation started (status=1).");
            
            UserService userService = new UserService();
            userService.saveUserInfo(userId, userData);

            return true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("!!! [ERROR] UserId: " + userId + " operation interrupted.");
            return false;
        } finally {
            // 5. 释放执行权：将状态重置回 0 (空闲)。
            status.set(0); 
            System.out.println("--- [RELEASE] UserId: " + userId + " status set back to 0.");
            
            // 6. 清理 Map：在状态释放后，移除 AtomicInteger 对象。
            // 必须使用 remove(key, value) 的重载版本来原子性地移除，
            // 确保移除的是当前线程操作的那个 status 对象，防止意外移除。
            userOperations.remove(userId, status);
            System.out.println("--- [CLEANUP] AtomicInteger for UserId: " + userId + " removed from Map.");
        }
    }

    // --- 测试代码 ---
    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerConcurrencyControlService controlService = new AtomicIntegerConcurrencyControlService();
        String userId1 = "user-A";
        String userId2 = "user-B";

        // 场景 1: 同一个ID连续提交两次 (只有第一次会执行)
        Thread t1a = new Thread(() -> controlService.submitUserInfo(userId1, "Data A1"), "AI-Thread-A1");
        Thread t1b = new Thread(() -> controlService.submitUserInfo(userId1, "Data A2"), "AI-Thread-A2");
        
        // 场景 2: 不同的ID可以并行提交 (会同时执行)
        Thread t2 = new Thread(() -> controlService.submitUserInfo(userId2, "Data B"), "AI-Thread-B");

        t1a.start(); 
        Thread.sleep(10); // 确保 A1 已经创建对象并尝试 CAS 到 1
        t1b.start();  // A2尝试 CAS，会失败并跳过
        t2.start();   // B 会同时开始执行
    }
}