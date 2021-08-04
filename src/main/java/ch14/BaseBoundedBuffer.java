package ch14;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @author: ymm
 * @date: 2021/8/4
 * @version: 1.0.0
 * @description: 14-2 有界缓存实现基类
 */
@ThreadSafe
public abstract class BaseBoundedBuffer<V> {
    @GuardedBy("this")
    private final V[] buf;
    @GuardedBy("this")
    private int tail;
    @GuardedBy("this")
    private int head;
    @GuardedBy("this")
    private int count;

    public BaseBoundedBuffer(int capacity) {
        this.buf = (V[]) new Object[capacity];
    }

    /**
     * 从缓存尾部放元素
     *
     * @param v
     */
    protected synchronized final void doPut(V v) {
        buf[tail] = v;
        if (++tail == buf.length) {
            tail = 0; // 缓存已满
        }
        ++count;
    }

    /**
     * 从缓存头部取元素
     *
     * @return
     */
    protected synchronized final V doTake() {
        V v = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0; // 缓存已空
        }
        --count;
        return v;
    }

    public synchronized final boolean isFull() {
        return count == buf.length;
    }

    public synchronized final boolean isEmpty() {
        return count == 0;
    }

    public static void main(String[] args) {

    }
}
