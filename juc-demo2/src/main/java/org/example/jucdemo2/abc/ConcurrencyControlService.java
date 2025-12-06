package org.example.jucdemo2.abc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 并发控制工具类，用于确保特定ID的操作在同一时间只能有一个在执行。
 * 在本例中，用于控制同一个UserId的保存操作不同时进行。
 */
public class ConcurrencyControlService {

    // 使用ConcurrentHashMap来存储正在执行操作的ID。
    // Key: 用户ID (String)
    // Value: 任意对象 (作为锁对象使用，可以是任何轻量级对象，如Object或Boolean)
    private final ConcurrentHashMap<String, Object> ongoingOperations = new ConcurrentHashMap<>();

    // 模拟一个数据库操作的耗时接口
    private static final class UserService {
        // 模拟一个耗时操作，使用AtomicLong来记录执行次数
        private static final AtomicLong executionCount = new AtomicLong(0);

        public boolean saveUserInfo(String userId, String data) throws InterruptedException {
            long currentCount = executionCount.incrementAndGet();
            System.out.println(">>> [START] Thread: " + Thread.currentThread().getName() + 
                               ", UserId: " + userId + 
                               ", Execution: " + currentCount);
            
            // 模拟耗时操作 (例如：数据库写入、远程API调用)
            Thread.sleep(2000); 

            System.out.println("<<< [END] Thread: " + Thread.currentThread().getName() + 
                               ", UserId: " + userId + 
                               ", Execution: " + currentCount + 
                               " -> Data saved successfully.");
            return true;
        }
    }

    /**
     * 提交用户信息的同步方法。
     * 保证同一个UserId在同一时间只有一个线程在执行保存操作。
     *
     * @param userId 用户的唯一ID
     * @param userData 用户提交的数据
     * @return true 如果保存操作开始执行；false 如果该ID的操作已经在进行中，本次提交被跳过。
     */
    public boolean submitUserInfo(String userId, String userData) {
        // 1. 尝试获取或创建该UserId的锁对象
        // putIfAbsent是原子操作，如果key不存在，则放入新的锁对象并返回null；
        // 如果key已存在，则返回已存在的value。
        Object lock = new Object();
        Object existingLock = ongoingOperations.putIfAbsent(userId, lock);

        if (existingLock != null) {
            // 2. 如果existingLock不为null，说明该UserId的操作正在进行中
            System.out.println("--- [SKIP] UserId: " + userId + 
                               " is already processing. Current submission skipped.");
            return false;
        }

        try {
            // 3. 只有成功将锁对象放入map的线程（即第一个到达的线程）才会执行到这里
            System.out.println("--- [PROCESS] UserId: " + userId + 
                               " operation started.");

            // 执行耗时的保存逻辑
            UserService userService = new UserService();
            userService.saveUserInfo(userId, userData);

            return true;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("!!! [ERROR] UserId: " + userId + " operation interrupted.");
            return false;
        } finally {
            // 4. 操作完成后，必须从map中移除该UserId，释放锁。
            // 移除操作也必须是原子且安全的，防止多线程问题。
            // 理论上，因为只有一个线程成功执行了putIfAbsent，所以直接用remove(key)即可。
            // 但是为了严谨性，可以使用 remove(key, value) 来确保移除的是我们放入的那个锁对象，
            // 尽管在这个简单场景下没有其他线程会修改。
            ongoingOperations.remove(userId, lock);
            System.out.println("--- [RELEASE] UserId: " + userId + " lock released.");
        }
    }

    // --- 测试代码 ---
    public static void main(String[] args) throws InterruptedException {
        ConcurrencyControlService controlService = new ConcurrencyControlService();
        String userId1 = "user-A";
        String userId2 = "user-B";
        String userId3 = "user-C";

        // 场景 1: 同一个ID连续提交两次 (只有第一次会执行)
        Thread t1a = new Thread(() -> controlService.submitUserInfo(userId1, "Data A1"), "Thread-A1");
        Thread t1b = new Thread(() -> controlService.submitUserInfo(userId1, "Data A2"), "Thread-A2");
        
        // 场景 2: 不同的ID可以并行提交 (会同时执行)
        //Thread t2 = new Thread(() -> controlService.submitUserInfo(userId2, "Data B"), "Thread-B");
        
        // 场景 3: 第三个ID (会同时执行)
        //Thread t3 = new Thread(() -> controlService.submitUserInfo(userId3, "Data C"), "Thread-C");

        // 启动线程
        t1a.start(); // A1开始执行
        // 稍等片刻，确保 A1 已经将锁放入 map
        Thread.sleep(10); 
        t1b.start(); // A2尝试执行，会被跳过
        //t2.start();  // B 会同时开始执行
        //t3.start();  // C 会同时开始执行
    }
}