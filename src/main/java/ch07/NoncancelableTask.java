package ch07;

import java.util.concurrent.BlockingQueue;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-7 不可取消的任务在退出前恢复中断
 */
public class NoncancelableTask {

    public Task getNextTask(BlockingQueue<Task> queue) {
        boolean interrupted = false;

        try {
            while (true) {
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted = true; // 设置中断状态
                    // 重新尝试
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt(); // 恢复中断
            }
        }

    }


    interface Task {

    }

}
