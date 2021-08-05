package ch16;

/**
 * @author: ymm
 * @date: 2021/8/5
 * @version: 1.0.0
 * @description: 16-1 如果在程序中没有包含足够的同步，那么可能产生奇怪的结果（不要这么做）
 */
public class PossibleReordering {
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        for (; ; ) {
            main1();
        }
    }

    public static void main1() throws InterruptedException {
        Thread one = new Thread() {
            @Override
            public void run() {
                a = 1;
                x = b;
            }
        };

        Thread other = new Thread() {
            @Override
            public void run() {
                b = 1;
                y = a;
            }
        };

        one.start();
        other.start();
        one.join();
        other.join();
        System.out.println("(" + x + "," + y + ")");

    }

}
