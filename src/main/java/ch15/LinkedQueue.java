package ch15;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: ymm
 * @date: 2021/8/4
 * @version: 1.0.0
 * @description: 15-7 Michael-Scott(Michael and Scott, 1996)非阻塞算法的插入算法
 */
@ThreadSafe
public class LinkedQueue<E> {
    private static class Node<E> {
        final E item;
        final AtomicReference<LinkedQueue.Node<E>> next;

        public Node(E item, LinkedQueue.Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<LinkedQueue.Node<E>>(next);
        }
    }

    private final LinkedQueue.Node<E> dummy = new LinkedQueue.Node<E>(null, null);
    private final AtomicReference<LinkedQueue.Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<LinkedQueue.Node<E>> tail = new AtomicReference<>(dummy);

    public boolean put(E item) {
        LinkedQueue.Node<E> newNode = new LinkedQueue.Node<E>(item, null);
        while (true) {
            LinkedQueue.Node<E> curtail = tail.get();
            LinkedQueue.Node<E> tailNext = curtail.next.get();
            if (curtail == tail.get()) {
                if (tailNext != null) {
                    // 队列处于中间状态，推进尾结点
                    tail.compareAndSet(curtail, tailNext);
                } else {
                    // 处于稳定状态，尝试插入新节点
                    if (curtail.next.compareAndSet(null, newNode)) {
                        // 插入操作成功，尝试推进尾结点
                        tail.compareAndSet(curtail, newNode);
                        return true;
                    }
                }
            }

        }
    }

}
