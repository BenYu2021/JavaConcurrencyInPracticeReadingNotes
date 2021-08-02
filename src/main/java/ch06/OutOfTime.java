package ch06;


import java.util.*;
// 静态导入
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 6-9 错误的Timer行为
 */
public class OutOfTime {

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(1); // 线程睡眠1s
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(5); // 线程睡眠5s
    }

    static class ThrowTask extends TimerTask {
        @Override
        public void run() {
            throw new RuntimeException();
        }
    }
}
