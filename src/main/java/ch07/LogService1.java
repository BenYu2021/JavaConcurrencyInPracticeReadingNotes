package ch07;

import net.jcip.annotations.GuardedBy;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.*;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-16 使用ExecutorService的日志服务（不完整的代码）
 */
public class LogService1 {
    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private final static long TIMEOUT = 1000;
    private final static TimeUnit UNIT = TimeUnit.SECONDS;
    private final PrintWriter writer;

    public LogService1(Writer writer) {
        this.writer = new PrintWriter(writer);
    }

    public void start() {

    }

    public void stop() {
        try {
            exec.shutdown();
            exec.awaitTermination(TIMEOUT, UNIT);
        } catch (InterruptedException e) {
            writer.close();
        }
    }

    public void log(String msg) throws InterruptedException {
        try {

            exec.execute(new WriteTask(msg));
        } catch (RejectedExecutionException ignored) {

        }

    }

    private class WriteTask implements Runnable {
        private String msg;

        public WriteTask(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {

        }
    }


}
