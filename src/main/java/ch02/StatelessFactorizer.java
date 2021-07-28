package ch02;

import net.jcip.annotations.ThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author: ymm
 * @date: 2021/7/27
 * @version: 1.0.0
 * @description: p14 程序清单2-1 一个无状态的servlet
 */
@ThreadSafe
public class StatelessFactorizer extends GenericServlet {


    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
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
