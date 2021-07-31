package ch04;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author: ymm
 * @date: 2021/7/30
 * @version: 1.0.0
 * @description: 4-5 与java.awt.Point类似的可变Point类（不要这么做）
 */
@NotThreadSafe
public class MutablePoint {
    // 公有
    public int x, y;

    public MutablePoint() {
        x = 0;
        y = 0;
    }

    /**
     * 从另一个同类型的MutablePoint对象创建该对象
     *
     * @param p
     */
    public MutablePoint(MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }
}
