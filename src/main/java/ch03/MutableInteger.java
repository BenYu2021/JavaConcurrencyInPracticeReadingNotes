package ch03;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author: ymm
 * @date: 2021/7/28
 * @version: 1.0.0
 * @description: 3-2 非线程安全的可变整数类
 */
@NotThreadSafe
public class MutableInteger {
    private int value;

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }

}
