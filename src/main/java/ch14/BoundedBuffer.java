package ch14;

/**
 * @author: ymm
 * @date: 2021/8/4
 * @version: 1.0.0
 * @description: 14-6 使用条件队列实现有界缓存
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

    // 条件谓词：not-full (!isFull)
    // 条件谓词：not-empty (!isEmpty)
    public BoundedBuffer() {
        super(100);
    }

    public BoundedBuffer(int capacity) {
        super(capacity);
    }

    /**
     * 阻塞并直到：not-full
     *
     * @param v
     * @throws InterruptedException
     */
    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }


    /**
     * 阻塞并直到：not-empty
     *
     * @return
     */
    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }

}
