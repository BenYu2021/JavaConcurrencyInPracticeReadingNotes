package ch03;

/**
 * @author: ymm
 * @date: 2021/7/29
 * @version: 1.0.0
 * @description: 不安全的发布
 */
public class StuffIntoPublic {
    public Holder holder;

    public void initialize() {
        holder = new Holder(42);
    }

}
