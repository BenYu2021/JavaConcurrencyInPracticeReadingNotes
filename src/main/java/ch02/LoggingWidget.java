package ch02;

/**
 * @author: ymm
 * @date: 2021/7/28
 * @version: 1.0.0
 * @description: p18 程序清单2-7 如果内置锁不是可重入的，那么这段代码将发生死锁
 */
public class LoggingWidget extends Widget {

    /**
     * 子类改写父类的synchronized方法，然后调用父类的方法，此时如果没有可重入的锁，那么这段代码将产生死锁
     */
    public synchronized void doSomething() {
        System.out.println(toString() + "calling doSomething");
        super.doSomething(); // 父类的synchronized方法
    }

}

