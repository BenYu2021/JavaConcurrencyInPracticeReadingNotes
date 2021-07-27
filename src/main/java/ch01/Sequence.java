package ch01;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author: ymm
 * @date: 2021/7/27
 * @version: 1.0.0
 * @description: p5：程序清单1-2 线性安全的数值序列生成器
 */
@ThreadSafe
public class Sequence {
    @GuardedBy("this")
    private int value;

    /**
     * synchronized内置锁
     * @return
     */
    public synchronized int getNext() {
        return value++;
    }

    public static void main(String[] args) {
        Sequence sequence = new Sequence();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println("sequence.getNext() = " + sequence.getNext());
            }).start();
        }
    }
}
