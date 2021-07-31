package ch04;

import net.jcip.annotations.ThreadSafe;

/**
 * @author: ymm
 * @date: 2021/7/31
 * @version: 1.0.0
 * @description: 4-11 线程安全且可变的Point类
 */
@ThreadSafe
public class SafePoint {
    private int x, y;

    private SafePoint(int a[]) {
        this(a[0], a[1]);
    }

    public SafePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized int[] get() {
        return new int[]{x, y};
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
