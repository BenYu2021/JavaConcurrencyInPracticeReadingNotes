package ch03;

import net.jcip.annotations.Immutable;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author: ymm
 * @date: 2021/7/29
 * @version: 1.0.0
 * @description: 3-12 对数值及其因式分解结果进行缓存的不可变容器类
 */
@Immutable
public class OneValueCache {
    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger i, BigInteger[] factors) {
        this.lastNumber = i;
        this.lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
        if (lastFactors == null || !lastFactors.equals(i)) {
            return null;
        } else {
            return Arrays.copyOf(lastFactors, lastFactors.length);
        }
    }
}

