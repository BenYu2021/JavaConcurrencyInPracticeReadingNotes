package ch03;

/**
 * @author: ymm
 * @date: 2021/7/28
 * @version: 1.0.0
 * @description: 在没有同步的情况下共享变量（不要这么做）
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println("number = " + number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
