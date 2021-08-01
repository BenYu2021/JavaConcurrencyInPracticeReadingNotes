package ch05;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 5-18 基于FutureTask的Memoizing封装器
 * 缺点：仍然存在两个线程计算出相同值的漏洞。
 */
public class Memoizer3<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }


    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        // if检查是原子的“先检查再执行”操作
        if (f == null) {
            Callable<V> eval = new Callable<>() {
                @Override
                public V call() throws InterruptedException {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f = ft;
            cache.put(arg, ft);
            ft.run(); // 这里将调用c.compute
        }

        try {
            return f.get();
        } catch (ExecutionException e) {
            throw LaunderThrowable.launderThrowable(e.getCause());
        }

    }
}
