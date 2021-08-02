package ch07;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-13 不支持关闭的生产者-消费者日志服务
 */
public class LogWriter {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private static final int CAPACITY = 1000;

    public LogWriter(Writer writer) {
        this.queue = new LinkedBlockingDeque<>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        public LoggerThread(Writer writer) {
            this.writer = new PrintWriter(writer, true); // 自动刷新
        }

        @Override
        public void run() {
            try {
                while (true) {
                    writer.println(queue.take());
                }
            } catch (InterruptedException ignored) {

            } finally {
                writer.close();
            }
        }
    }


}
