package ch07;

import ch05.LaunderThrowable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-9 在专门的线程中中断任务（！！！看不懂）
 */
public class TimedRun2 {
    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(1);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() {
                if (t != null) {
                    throw LaunderThrowable.launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();

    }
}
