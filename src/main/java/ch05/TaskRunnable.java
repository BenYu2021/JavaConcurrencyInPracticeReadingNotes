package ch05;

import java.util.concurrent.BlockingQueue;

/**
 * @author: ymm
 * @date: 2021/7/31
 * @version: 1.0.0
 * @description: 5-10 恢复中断状态以避免屏蔽中断
 */
public class TaskRunnable implements Runnable {
    BlockingQueue<Task> queue;

    @Override
    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {
            // 恢复被中断的状态
            Thread.currentThread().interrupt();
        }
    }

    void processTask(Task task) {
        // 处理任务
    }

    interface Task {

    }
}
