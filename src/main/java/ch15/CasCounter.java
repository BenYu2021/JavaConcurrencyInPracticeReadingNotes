package ch15;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: ymm
 * @date: 2021/8/4
 * @version: 1.0.0
 * @description: 15-2 基于CAS实现的非阻塞计数器
 */
public class CasCounter {
    private SimulateCAS value;

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v + 1));
        return v + 1;
    }
}
