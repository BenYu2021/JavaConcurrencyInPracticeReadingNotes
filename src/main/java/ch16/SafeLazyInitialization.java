package ch16;

import net.jcip.annotations.ThreadSafe;

/**
 * @author: ymm
 * @date: 2021/8/5
 * @version: 1.0.0
 * @description: 16-4 线程安全的延迟初始化
 */
@ThreadSafe
public class SafeLazyInitialization {
    private static UnsafeLazyInitialization.Resource resource;

    public synchronized static UnsafeLazyInitialization.Resource getInstance() {
        if (resource == null) {
            resource = new UnsafeLazyInitialization.Resource();
        }
        return resource;
    }

    static class Resource {

    }
}
