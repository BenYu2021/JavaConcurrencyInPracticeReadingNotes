package ch01;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author: ymm
 * @date: 2021/7/27
 * @version: 1.0.0
 * @description: p5：程序清单1-1 非线性安全的数值序列生成器
 */

@NotThreadSafe
public class UnsafeSequence01 {
    private int value;

    /**
     * 返回一个唯一的数值
     *
     * @return
     */
    public int getNext() {
        System.out.println("value++ = " + value++);
        return value;
    }

    public static void main(String[] args) {
        UnsafeSequence01 unsafeSequence = new UnsafeSequence01();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                System.out.println("unsafeSequence.getNext() = " + unsafeSequence.getNext());
            }).start();
        }
    }
}
