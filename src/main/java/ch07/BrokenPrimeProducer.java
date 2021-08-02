package ch07;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-3 不可靠的取消操作将把生产者置于阻塞的操作中（不要这么做）
 */
public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean canceled = false;

    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!canceled) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException consumed) {

        }
    }

    public void cancel() {
        canceled = true;
    }

    void consumePrimes() throws InterruptedException {
        BlockingQueue<BigInteger> primes = null;
        BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
        producer.start();


        try {
            while (needMorePrimes()) {
                consume(primes.take());
            }
        } finally {
            producer.cancel();
        }

    }

    private boolean needMorePrimes() {
        return false;
    }

    void consume(BigInteger bigInteger) {

    }
}

