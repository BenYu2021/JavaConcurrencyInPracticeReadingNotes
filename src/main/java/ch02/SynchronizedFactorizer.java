package ch02;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: ymm
 * @date: 2021/7/27
 * @version: 1.0.0
 * @description: p20 程序清单2-6 这个Servlet能正确地缓存最新的计算结果，但并发性却非常糟糕（不要这么做）
 */
@ThreadSafe
public class SynchronizedFactorizer extends GenericServlet {

    /**
     * lastNumber和lastFactors是有联系的
     */
    @GuardedBy("this")
    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<BigInteger>();
    @GuardedBy("this")
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<BigInteger[]>();


    @Override
    public synchronized void service(ServletRequest req, ServletResponse resp) {
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
