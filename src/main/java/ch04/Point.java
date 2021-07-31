package ch04;

import net.jcip.annotations.Immutable;

/**
 * @author: ymm
 * @date: 2021/7/31
 * @version: 1.0.0
 * @description: DelegatingVehicleTracker中使用的不可变Point类
 */
@Immutable
public class Point {
    private final int x,y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
