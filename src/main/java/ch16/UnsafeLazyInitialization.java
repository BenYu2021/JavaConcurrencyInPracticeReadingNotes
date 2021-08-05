package ch16;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author: ymm
 * @date: 2021/8/5
 * @version: 1.0.0
 * @description: 16-3 不安全的延迟初始化（不要这么做）
 */
@NotThreadSafe
public class UnsafeLazyInitialization {
    private static Resource resource;

    public static Resource getInstance() {
        if (resource == null) {
            resource = new Resource();
        }
        return resource;
    }

    static class Resource {

    }
}
