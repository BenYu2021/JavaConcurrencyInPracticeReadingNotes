package ch07;

import ch05.LaunderThrowable;

import java.util.concurrent.*;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-10 通过Future来取消任务
 */
public class TimedRun {
    private static final ExecutorService taskExec = Executors.newCachedThreadPool();

    public static void timeRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> task = taskExec.submit(r);

        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            // 接下来任务将被取消
        } catch (ExecutionException e) {
            // 如果任务中抛出了异常，那么重新抛出异常
            throw LaunderThrowable.launderThrowable(e.getCause());
        } finally {
            // 如果任务已经结束，那么执行取消操作也不会带来任何影响
            task.cancel(true); //
        }
    }
}
