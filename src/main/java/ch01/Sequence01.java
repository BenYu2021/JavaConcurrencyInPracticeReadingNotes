package ch01;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author: ymm
 * @date: 2021/7/27
 * @version: 1.0.0
 * @description:
 */
@ThreadSafe
public class Sequence01 {
    @GuardedBy("this")
    private int value;

    public synchronized int getNext() {
        System.out.println("value++ = " + value++);
        return value;
    }

    public static void main(String[] args) {
        Sequence01 sequence = new Sequence01();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println("sequence.getNext() = " + sequence.getNext());
            }).start();
        }
    }
}
