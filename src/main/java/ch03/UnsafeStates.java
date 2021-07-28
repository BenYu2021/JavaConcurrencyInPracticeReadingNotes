package ch03;

/**
 * @author: ymm
 * @date: 2021/7/28
 * @version: 1.0.0
 * @description: 3-6 使内部类的可变状态逸出（不要这么做）
 */
public class UnsafeStates {

    private String[] states = new String[]{
            "AK", "AL"
    };

    public String[] getStates() {
        return states;
    }
}
