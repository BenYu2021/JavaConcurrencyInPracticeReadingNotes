package ch03;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;

/**
 * @author: ymm
 * @date: 2021/7/28
 * @version: 1.0.0
 * @description: 3-2 线程安全的可变整数类
 */
@NotThreadSafe
public class SynchronizedInteger {
    @GuardedBy("this")
    private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized void set(int value) {
        this.value = value;
    }

}
