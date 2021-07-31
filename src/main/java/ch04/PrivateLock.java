package ch04;

import ch02.Widget;
import net.jcip.annotations.GuardedBy;

/**
 * @author: ymm
 * @date: 2021/7/30
 * @version: 1.0.0
 * @description:
 */
public class PrivateLock {
    private final Object myLock = new Object();

    @GuardedBy("myLock")
    Widget widget;

    void someMethod() {
        synchronized (myLock) {
            // 访问或修改Widget的状态
        }
    }

}
