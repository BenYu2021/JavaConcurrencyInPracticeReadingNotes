package ch05;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 用CurrentHashMap替换HashMap
 * 缺点：当两个线程同时调用conpute时存在一个漏洞，可能会导致计算得到相同的值。
 */
public class Memoizer2<A, V> implements Computable<A, V> {
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer2(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

}
