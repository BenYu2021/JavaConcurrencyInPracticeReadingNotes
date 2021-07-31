package ch04;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: ymm
 * @date: 2021/7/31
 * @version: 1.0.0
 * @description: 4-7 将线程安全委托给ConcurrentHashMap
 * delegating
 * v.	授(权); 把(工作、权力等)委托(给下级); 选派(某人做某事);
 */
public class DelegatingVehicleTracker {
    private final ConcurrentHashMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points) {
        // 把Map转换为ConcurrentHashMap
        locations = new ConcurrentHashMap<>(points);
        // 把locations转换为不可修改的集合
        this.unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null) {
            throw new IllegalStateException("invalid vehicle name: " + id);
        }
    }

    /**
     * 4-8 返回locations的静态拷贝而非实时拷贝
     *
     * @return
     * 返回对locations这个Map对象的一个浅拷贝。由于Map内容是不可变的，因此只需复制Map的结构，而不复制它的内容。
     */
    public Map<String, Point> getLocationsAsStatic() {

        return Collections.unmodifiableMap(new HashMap<>(locations));
    }
}
