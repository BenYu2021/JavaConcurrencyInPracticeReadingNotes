package ch15;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author: ymm
 * @date: 2021/8/4
 * @version: 1.0.0
 * @description: 15-1 模拟CAS操作
 */
@ThreadSafe
public class SimulateCAS {
    @GuardedBy("this")
    private int value;

    public synchronized int get() {
        return value;
    }

    /**
     * 如果value与期望的值expectedValue相同，则设置新值，否则什么也不做
     *
     * @param expectedValue
     * @param newValue
     * @return
     */
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }

    /**
     * 期望值expectedValue是否与原值相同
     * @param expectedValue
     * @param newValue
     * @return
     */
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return (expectedValue == compareAndSwap(expectedValue, newValue));
    }

}
