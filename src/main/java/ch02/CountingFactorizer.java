package ch02;

import net.jcip.annotations.NotThreadSafe;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: ymm
 * @date: 2021/7/27
 * @version: 1.0.0
 * @description: p18 程序清单2-4 使用AtomicLong类型的变量来统计已处理请求的数量
 */
@NotThreadSafe
public class CountingFactorizer extends GenericServlet {

    private final AtomicLong count = new AtomicLong(0);

    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        count.incrementAndGet();
        encodeIntoResponse(resp, factors);
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
