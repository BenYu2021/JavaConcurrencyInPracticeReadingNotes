package ch14;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author: ymm
 * @date: 2021/8/4
 * @version: 1.0.0
 * @description: 14-14 使用AbstractQueuedSynchronizer实现二元闭锁
 */
public class OneShotLatch {
    private final Sync sync = new Sync();

    public void signal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int ignored) {
            // 如果闭锁是开的 (stats == 1)，那么操作将成功，否则将失败
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int ignored) {
            setState(1); // 现在打开闭锁
            return true;
        }
    }
}
