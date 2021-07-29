package ch03;

/**
 * @author: ymm
 * @date: 2021/7/29
 * @version: 1.0.0
 * @description: 3-15 由于未被正确发布，因此这个类可能出现故障
 * sanity
 * 精神健全; 神志正常; 明智; 理智; 通情达理;
 */
public class Holder {
    private int n;

    /**
     * Object构造函数会在子类构造函数运行前将默认值写入所有域
     * @param n
     */
    public Holder(int n) {
        this.n = n;
    }

    public void assertSanity() {
        if (n != n) {
            throw new AssertionError("This statement is false");
        }
    }

}
