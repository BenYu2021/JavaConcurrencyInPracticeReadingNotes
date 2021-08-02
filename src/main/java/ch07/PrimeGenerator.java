package ch07;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-1 使用volatile类型的域来保护取消状态
 */
@ThreadSafe
public class PrimeGenerator implements Runnable {
    @GuardedBy("this")
    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean canceled = false;

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!canceled) {
            p = p.nextProbablePrime(); // 返回一个整数大于该BigInteger的素数
            synchronized (this) {
                primes.add(p);
            }
        }
    }

    public void cancel() {
        canceled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<BigInteger>(primes);
    }

    /**
     * 7-2 一个运行1s的素数生成器
     * @return
     * @throws InterruptedException
     */
    static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            SECONDS.sleep(1);
        } finally {
            generator.cancel();
        }
        return generator.get();
    }

    // 测试
    public static void main(String[] args) throws InterruptedException {
        List<BigInteger> primes = aSecondOfPrimes();
        for (BigInteger prime : primes) {
            System.out.println("prime = " + prime);
        }
    }
}
