package ch03;

import net.jcip.annotations.Immutable;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: ymm
 * @date: 2021/7/29
 * @version: 1.0.0
 * @description: 3-11 在可变对象的基础上构建不可变类
 * stooges
 * n.    受人驱使的人; 奴才; 走狗; (供其他演员作弄打趣的)丑角;
 */
@Immutable
public class ThreeStooges {
    private final Set<String> stooges = new HashSet<>();

    public ThreeStooges() {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    public boolean isStooges(String name) {
        return stooges.contains(name);
    }
}
