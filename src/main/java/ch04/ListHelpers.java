package ch04;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: ymm
 * @date: 2021/7/31
 * @version: 1.0.0
 * @description:
 */

/**
 * 4-14 非线程安全的“若没有则添加”（不要这么做）
 *
 * @param <E>
 */
@NotThreadSafe
class BadListHelper<E> {
    // list是public
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    /**
     * 所有链表操作都被声明为synchronized，但却使用了不同的锁，putIfAbsent相对于链表的操作不是原子的
     *
     * @param x
     * @return
     */
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
        if (absent) {
            list.add(x);
        }
        return absent;
    }

}

/**
 * 4-15 通过客户端加锁来实现“若没有则添加”
 *
 * @param <E>
 */
@ThreadSafe
class GoodListHelper<E> {
    // list是public
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    /**
     * 要想使这个方法正确执行，必须使List在实现客户端加锁或外部加锁时使用同一个锁。
     * 客户端锁是指，对于使用某个对象X的客户端代码，使用X本身用于保护其状态的锁来保护这段客户端代码。
     * @param x
     * @return
     */
    public boolean putIfAbsent(E x) {
        //
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent) {
                list.add(x);
            }
            return absent;
        }
    }
}
