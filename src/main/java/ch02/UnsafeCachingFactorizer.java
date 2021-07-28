package ch02;

import net.jcip.annotations.NotThreadSafe;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: ymm
 * @date: 2021/7/27
 * @version: 1.0.0
 * @description: p19 程序清单2-5 该servlet在没有足够原子性保证的情况下对其最近计算结果进行缓存（不要这么做）
 */
@NotThreadSafe
public class UnsafeCachingFactorizer extends GenericServlet {

    /**
     * lastNumber和lastFactors是有联系的
     */
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<BigInteger>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<BigInteger[]>();


    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);

        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(resp, lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(resp, factors);
        }


    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {

    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // 不是真正的因子
        return new BigInteger[]{i};
    }
}
