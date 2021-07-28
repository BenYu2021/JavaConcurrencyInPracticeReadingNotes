package ch02;

/**
 * @author: ymm
 * @date: 2021/7/28
 * @version: 1.0.0
 * @description: p1 程序清单2-3 延迟初始化中的竞态条件（不要这么做）
 */
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }
}

class ExpensiveObject {
}