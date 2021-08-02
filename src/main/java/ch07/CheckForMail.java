package ch07;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-20 使用私有的Executor，并且该Executor的生命周期受限于方法调用
 */
public class CheckForMail {
    public boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        final AtomicBoolean hasNewMail = new AtomicBoolean(false);

        try {
            for (final String host : hosts) {
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (checkMail(host)) {
                            hasNewMail.set(true);
                        }
                    }
                });
            }
        } finally {
            exec.shutdown();
            exec.awaitTermination(timeout, unit);
        }

        return hasNewMail.get();
    }

    private boolean checkMail(String host) {
        // 检查邮件
        return false;
    }

}
