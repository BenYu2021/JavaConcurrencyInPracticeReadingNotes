package ch05;

import java.util.concurrent.CountDownLatch;

/**
 * @author: ymm
 * @date: 2021/7/31
 * @version: 1.0.0
 * @description: 5-11 在计时测试中使用CountDownLatch来启动和停止线程
 * Latch 锁存器;闩锁;闭锁
 */
public class TestHarness {
    public long timeTask(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {

                    }
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return (end - start);
    }

}
