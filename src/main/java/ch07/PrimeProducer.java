package ch07;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-5 通过中断来取消
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void                                                                                                                                                                                                            run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException consumed) {
            /* 允许线程退出 */
        }
    }

    public void cancel() {
        interrupt();
    }
}
