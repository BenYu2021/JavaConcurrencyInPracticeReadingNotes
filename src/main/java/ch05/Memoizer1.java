package ch05;

import net.jcip.annotations.GuardedBy;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 5-16 使用HashMap和同步机制来初始化缓存
 * 缺点：对整个compute方法进行同步，每次只有一个线程能够执行compute方法。
 */

public class Memorizer1<A, V> implements Computable<A, V> {
    @GuardedBy("this")
    private final Map<A, V> cache = new HashMap<>();
    private final Computable<A, V> c;

    public Memorizer1(Computable<A, V> c) {
        this.c = c;
    }

    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}

interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}

class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        // after deep thought
        return new BigInteger(arg);
    }
}