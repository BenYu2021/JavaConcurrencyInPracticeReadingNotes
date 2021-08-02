package ch07;

import net.jcip.annotations.GuardedBy;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-15 向LogWriter添加可靠的取消操作
 */
public class LogService {
    private final BlockingQueue<String> queue;
    private final LogerThread logerThread;
    private final PrintWriter writer;
    @GuardedBy("this")
    private boolean isShutdown;
    @GuardedBy("this")
    private int reservations;


    public LogService(Writer writer) {
        this.queue = new LinkedBlockingDeque<String>();
        this.logerThread = new LogerThread();
        this.writer = new PrintWriter(writer);
    }

    public void start() {
        logerThread.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        logerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown) {
                throw new IllegalStateException();
            }
            ++reservations;
        }
        queue.put(msg);
    }

    private class LogerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogService.this) {
                            if (isShutdown && reservations == 0) {
                                break;
                            }
                            String msg = queue.take();
                            synchronized (LogService.this) {
                                --reservations;
                            }
                            writer.println(msg);
                        }
                    } catch (InterruptedException e) {
                        /* 重试 */
                    }
                }
            } finally {
                writer.close();
            }
        }
    }

}
