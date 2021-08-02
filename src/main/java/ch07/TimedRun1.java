package ch07;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-8 在外部线程中安排中断（不要这么做）
 * 破坏了规则：中断线程之前，应该了解它的中断策略。
 */
public class TimedRun1 {
    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(1);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);
        r.run();
    }
}
