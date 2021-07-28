package ch02;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;

/**
 * @author: ymm
 * @date: 2021/7/27
 * @version: 1.0.0
 * @description: p15 程序清单2-2 在没有同步的情况下统计已处理请求数量的servlet（不要这么做）
 */
@NotThreadSafe
public class UnsafeCountingFactorizer extends GenericServlet {

    private long count;

    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        ++count;
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
