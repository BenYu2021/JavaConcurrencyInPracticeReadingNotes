package ch06;

import java.util.concurrent.*;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 6-16 指定时间内获取广告信息
 */
public class RenderWithTimeBudget {
    private static final Ad DEFAULT_AD = new Ad(); // 默认的广告
    private static final long TIME_BUDGET = 1000;
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    // 渲染页面广告
    Page renderPageWithAd() throws InterruptedException {
        long endNanos = System.nanoTime() + TIME_BUDGET;
        Future<Ad> f = exec.submit(new FetchAdTask());

        // 在等待广告的同时显示页面
        Page page = renderPageBody();
        Ad ad;
        try {
            // 只等待指定时间长度
            long timeLeft = endNanos - System.nanoTime();
            ad = f.get(timeLeft, TimeUnit.NANOSECONDS);
        } catch (ExecutionException e) {
            ad = DEFAULT_AD;
        } catch (TimeoutException e) {
            ad = DEFAULT_AD;
            // 加载广告超时
            f.cancel(true);
        }

        page.setAd(ad);
        return page;
    }

    // 渲染页面
    Page renderPageBody() {
        return new Page();
    }

    // 广告
    static class Ad {

    }

    // 页面
    static class Page {
        public void setAd(Ad ad) {

        }
    }

    static class FetchAdTask implements Callable<Ad> {
        @Override
        public Ad call() throws Exception {
            return new Ad();
        }
    }
}
