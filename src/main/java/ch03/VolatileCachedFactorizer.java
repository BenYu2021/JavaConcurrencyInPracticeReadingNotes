package ch03;

import net.jcip.annotations.ThreadSafe;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author: ymm
 * @date: 2021/7/29
 * @version: 1.0.0
 * @description:
 */
@ThreadSafe
public class VolatileCachedFactorizer extends GenericServlet {
    private volatile OneValueCache cache = new OneValueCache(null, null);

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = cache.getFactors(i);
        if (factors == null) {
            factors = factor(i);
            cache = new OneValueCache(i, factors);
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
