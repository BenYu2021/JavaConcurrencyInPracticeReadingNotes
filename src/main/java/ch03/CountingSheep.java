package ch03;

/**
 * @author: ymm
 * @date: 2021/7/28
 * @version: 1.0.0
 * @description: 3-4 数绵羊
 */
public class CountingSheep {
    volatile boolean asleep;

    public void tryToSleep(String[] args) {
        while (!asleep)
            countSomeSheep();
    }

    private void countSomeSheep() {
        // 1,2,3...
    }

}
