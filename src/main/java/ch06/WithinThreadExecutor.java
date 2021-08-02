package ch06;

import java.util.concurrent.Executor;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 6-6 在调用线程中以同步方式执行所有任务的Executor
 */
public class WithinThreadExecutor implements Executor {
    @Override
    public void execute(Runnable r) {
        r.run();
    }
}
