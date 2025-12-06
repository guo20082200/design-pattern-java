package org.example.jucdemo2.abc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 并发控制工具类，使用 ReentrantLock 实现 ID 级别的并发控制。
 */
public class ReentrantLockConcurrencyControlService {

    // 存储每个用户ID对应的可重入锁。
    // Key: 用户ID (String)
    // Value: 对应的 ReentrantLock 对象
    private final ConcurrentHashMap<String, Lock> userLocks = new ConcurrentHashMap<>();

    // 模拟一个数据库操作的耗时接口
    private static final class UserService {
        private static final AtomicLong executionCount = new AtomicLong(0);

        public boolean saveUserInfo(String userId, String data) throws InterruptedException {
            long currentCount = executionCount.incrementAndGet();
            System.out.println(">>> [START] Thread: " + Thread.currentThread().getName() + 
                               ", UserId: " + userId + 
                               ", Execution: " + currentCount);
            
            // 模拟耗时操作 (2秒)
            Thread.sleep(2000); 

            System.out.println("<<< [END] Thread: " + Thread.currentThread().getName() + 
                               ", UserId: " + userId + 
                               ", Execution: " + currentCount + 
                               " -> Data saved successfully.");
            return true;
        }
    }

    /**
     * 提交用户信息的同步方法 (基于 ReentrantLock)。
     * * @param userId 用户的唯一ID
     * @param userData 用户提交的数据
     * @return true 如果保存操作开始执行；false 如果该ID的操作已经在进行中，本次提交被跳过。
     */
    public boolean submitUserInfo(String userId, String userData) {
        
        // 1. 获取或创建该UserId的锁对象。
        // computeIfAbsent 是原子操作，确保只会创建一个 Lock 对象。
        Lock lock = userLocks.computeIfAbsent(userId, k -> new ReentrantLock());
        
        // 2. 尝试获取锁：使用 tryLock() 实现非阻塞获取。
        boolean locked = lock.tryLock();

        if (!locked) {
            // 3. 如果未能立即获取锁，说明操作正在进行中，本次提交跳过。
            System.out.println("--- [SKIP] UserId: " + userId + 
                               " is already processing. Current submission skipped.");
            
            // 注意：这里没有成功执行业务，但 Lock 对象仍留在 Map 中。
            return false;
        }

        try {
            // 4. 成功获取锁，执行耗时的保存逻辑。
            System.out.println("--- [PROCESS] UserId: " + userId + 
                               " operation started (Locked).");
            
            UserService userService = new UserService();
            userService.saveUserInfo(userId, userData);

            return true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("!!! [ERROR] UserId: " + userId + " operation interrupted.");
            return false;
        } finally {
            // 5. 释放锁。这一步是 ReentrantLock 模式的关键且必须的操作。
            if (locked) {
                lock.unlock();
                System.out.println("--- [RELEASE] UserId: " + userId + " lock unlocked.");

                // 6. 清理 Lock 对象（防止内存泄漏）。
                // 只有当这是最后一个持有该锁的线程时（即 lock.getQueueLength() == 0），
                // 并且它不再被任何线程持有（lock.hasQueuedThreads() 并不准确），
                // 为了简单和安全，通常选择在每次释放后尝试清理。
                // *警告：精确清理 Lock 对象的逻辑非常复杂，本例采取简单移除策略。
                // 在生产环境中，这需要更细致的判断，例如使用 WeakHashMap 或定期清理任务。
                // 在本例中，因为我们使用 tryLock() 且没有线程排队等待，我们可以简化处理。
                userLocks.remove(userId, lock); 
                System.out.println("--- [CLEANUP] Lock for UserId: " + userId + " removed from Map.");
            }
        }
    }

    // --- 测试代码 (与上一个示例相同) ---
    public static void main(String[] args) throws InterruptedException {
        ReentrantLockConcurrencyControlService controlService = new ReentrantLockConcurrencyControlService();
        String userId1 = "user-A";
        String userId2 = "user-B";

        Thread t1a = new Thread(() -> controlService.submitUserInfo(userId1, "Data A1"), "RL-Thread-A1");
        Thread t1b = new Thread(() -> controlService.submitUserInfo(userId1, "Data A2"), "RL-Thread-A2");
        Thread t2 = new Thread(() -> controlService.submitUserInfo(userId2, "Data B"), "RL-Thread-B");

        t1a.start(); 
        Thread.sleep(10); // 确保 A1 已经获取锁
        t1b.start();  // A2尝试执行，会被跳过
        t2.start();   // B 会同时开始执行
    }
}