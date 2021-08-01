package ch05;

import java.util.concurrent.*;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 5-19 Memoizer的最终实现
 */
public class Memoizer<A, V> implements Computable<A, V> {

    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                // 使用了原子操作
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (ExecutionException e) {
                throw LaunderThrowable.launderThrowable(e.getCause());
            } catch (CancellationException e) {
                cache.remove(arg, f);
            }
        }
    }

}
