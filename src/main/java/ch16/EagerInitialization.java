package ch16;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author: ymm
 * @date: 2021/8/5
 * @version: 1.0.0
 * @description: 16-5 提前初始化
 */
@NotThreadSafe
public class EagerInitialization {
    private static Resource resource = new Resource();

    public static Resource getInstance() {
        return resource;
    }

    static class Resource {

    }
}
