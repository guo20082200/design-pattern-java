package org.example.jucdemo2.juc.future01;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * FutureTask的最佳实践：
 * 使用Callable接口作为任务的参数，以便获取任务的执行结果。
 * 谨慎处理任务的取消操作，确保任务在取消后能正确清理资源。
 * 避免在任务执行过程中阻塞主线程，尽量使用异步回调或者其他并发手段来处理任务的结果。
 * 合理利用线程池来执行FutureTask，避免创建过多线程导致资源浪费和性能下降。
 * @author zishi
 */
//public class FutureTaskDemo {
//
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//
//
//        FutureTask<String> futureTask = new FutureTask<>(() -> "hello");
//
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(futureTask);
//
//        // 阻塞
//        String s = futureTask.get();
//        System.out.println(s);
//
//        executor.shutdown();
//
//
//    }
//}
/**
1. FutureTask的应用场景

异步计算任务：FutureTask可以方便地执行异步计算任务，并在计算完成后获取结果。
缓存的使用：可以使用FutureTask来实现简单的缓存功能，当缓存中不存在指定值时，通过FutureTask来计算并缓存结果。
并发任务的控制：可以利用FutureTask的特性来实现对一组并发任务的控制和管理，例如等待所有任务完成或只等待其中一个任务完成。
异步IO操作：FutureTask可以用于异步IO操作的处理，例如异步读写文件或网络请求，通过FutureTask获取IO操作的结果。
并行计算：FutureTask可以用于将一个大任务划分为多个小任务，并行计算，最后合并计算结果。

2. FutureTask的局限性
尽管FutureTask是一个强大的工具，但也存在一些局限性需要注意：

无法获取任务执行进度：FutureTask无法直接获取任务的执行进度，只能获取任务的执行结果。
无法动态取消任务：一旦FutureTask进入运行状态，就无法再取消任务，只能等待任务执行完成。
单次使用：每个FutureTask只能执行一次，如果需要再次执行，需要创建新的FutureTask对象。
*/
