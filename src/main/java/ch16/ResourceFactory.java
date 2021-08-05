package ch16;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author: ymm
 * @date: 2021/8/5
 * @version: 1.0.0
 * @description: 16-6 延长初始化占位模式
 */
@ThreadSafe
public class ResourceFactory {

    private static class ResourceHolder {
        public static Resource resource = new Resource();
    }

    public static Resource getResource() {
        return ResourceHolder.resource;
    }

    static class Resource {

    }
}
