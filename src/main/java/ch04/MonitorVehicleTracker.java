package ch04;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ymm
 * @date: 2021/7/30
 * @version: 1.0.0
 * @description: 4-4 基于监视器模式的车辆追踪
 */
@ThreadSafe
public class MonitorVehicleTracker {

    // 车辆标记和位置
    @GuardedBy("this")
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    /**
     * 获取所有车辆的位置
     *
     * @return
     */
    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    /**
     * 根据车辆标记获坐标
     *
     * @param id
     * @return
     */
    public synchronized MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint loc = locations.get(id);
        if (loc == null) {
            throw new IllegalCallerException("No such ID: " + id);
        }
        loc.x = x;
        loc.y = y;
    }

    /**
     * @param m
     * @return
     */
    private Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        HashMap<String, MutablePoint> result = new HashMap<>();
        for (String id : m.keySet()) {
            result.put(id, new MutablePoint(m.get(id)));
        }
        return Collections.unmodifiableMap(result);
    }
}
