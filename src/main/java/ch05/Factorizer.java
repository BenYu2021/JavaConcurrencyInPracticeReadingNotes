package ch05;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 5-20 在因式分解servlet中使用Memoizer来缓存结果
 */
public class Factorizer extends GenericServlet implements Servlet {

    private final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>() {
        @Override
        public BigInteger[] compute(BigInteger arg) throws InterruptedException {
            return factor(arg);
        }
    };
    private final Computable<BigInteger, BigInteger[]> cache = new Memoizer<>(c);


    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        try {
            BigInteger i = extractFromRequest(req);
            encodeIntoResponse(resp, cache.compute(i));
        } catch (InterruptedException e) {
            encodeError(resp, "factorization interrupted");
        }
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    void encodeError(ServletResponse resp, String errorString) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // 不是真正的因式分解，暂未实现
        return new BigInteger[]{i};
    }
}
