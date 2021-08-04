package ch14;

import net.jcip.annotations.GuardedBy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: ymm
 * @date: 2021/8/4
 * @version: 1.0.0
 * @description: 14-11 使用显示条件变量的有界缓存
 */
public class ConditionBoundedBuffer<T> {
    protected final Lock lock = new ReentrantLock();
    // 条件谓词：notFull (count < items.length)
    private final Condition notFull = lock.newCondition();
    // 条件谓词：notEmpty (count > 0)
    private final Condition notEmpty = lock.newCondition();
    public static final int BUFFER_SIZE = 100;

    @GuardedBy("lock")
    private final T[] items = (T[]) new Object[BUFFER_SIZE];
    @GuardedBy("lock")
    private int tail, head, count;

    /**
     * 阻塞直到：notFull
     *
     * @param x
     * @throws InterruptedException
     */
    public void put(T x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) { // 缓存已满
                notFull.await(); // 等待缓存不满的信号
            }
            items[tail] = x; // 向队尾存元素
            if (++tail == items.length) { // 放完最后一个元素
                tail = 0;
            }
            ++count;
            notEmpty.signal(); // 发送缓存非空的信号
        } finally {
            lock.unlock();
        }
    }

    /**
     * 阻塞并直到：notFull
     *
     * @return
     * @throws InterruptedException
     */
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) { // 缓存为空
                notEmpty.await(); //等待缓存非空的信号
            }
            T x = items[head]; // 从队头取元素
            items[head] = null;
            if (++head == items.length) { // 取完最后一个元素
                head = 0;
            }
            --count;
            notFull.signal(); // 发送非空的信号
            return x;
        } finally {
            lock.unlock();
        }
    }

}
