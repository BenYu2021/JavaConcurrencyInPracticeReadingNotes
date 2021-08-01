package ch05;

import net.jcip.annotations.GuardedBy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author: ymm
 * @date: 2021/7/31
 * @version: 1.0.0
 * @description: 5-6 隐藏在字符串连接中的迭代操作
 */
public class HiddenIterator {
    @GuardedBy("this")
    private final Set<Integer> set = new HashSet<Integer>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addTenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            add(r.nextInt());
            System.out.println("DEBUG: added ten elements to " + set);
        }
    }

}
