package ch16;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author: ymm
 * @date: 2021/8/5
 * @version: 1.0.0
 * @description: 16-7 双重检查锁（不要这么做）
 *
 */
@NotThreadSafe
public class DoubleCheckedLocking {
    private static Resource resource; // 需要加volatile

    public static Resource getResource() {
        if (resource == null) {
            synchronized (DoubleCheckedLocking.class) {
                if (resource == null) {
                    resource = new Resource();
                }
            }
        }
        return resource;
    }

    static class Resource {

    }
}
