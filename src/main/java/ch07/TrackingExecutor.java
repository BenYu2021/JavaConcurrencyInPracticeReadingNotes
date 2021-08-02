package ch07;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-21 在ExecutorService中跟踪关闭之后被取消的任务
 */
public class TrackingExecutor extends AbstractExecutorService {
    private final ExecutorService exec;
    private final Set<Runnable> taskCancelledAtShutdown =
            Collections.synchronizedSet(new HashSet<Runnable>());

    public TrackingExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    @Override
    public void shutdown() {
        exec.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return exec.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout, unit);
    }

    public List<Runnable> getCancelledTasks() {
        if (!exec.isShutdown()) {
            throw new IllegalStateException(/*...*/);
        }
        return new ArrayList<Runnable>(taskCancelledAtShutdown);
    }

    @Override
    public void execute(final Runnable runnable) {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {
                    if (isShutdown() && Thread.currentThread().isInterrupted()) {
                        taskCancelledAtShutdown.add(runnable);
                    }
                }
            }
        });
    }
}
