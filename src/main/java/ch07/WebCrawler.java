package ch07;

import net.jcip.annotations.GuardedBy;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-22 使用TrackingExecutorService来保存未完成的任务以备后续执行
 */
public abstract class WebCrawler {
    private volatile TrackingExecutor exec;
    @GuardedBy("this")
    private final Set<URL> urlsToCrawl = new HashSet<URL>();
    private static final long TIMEOUT = 500;
    private static final TimeUnit UNIT = TimeUnit.MILLISECONDS;
    private final ConcurrentMap<URL, Boolean> seen = new ConcurrentHashMap<>();


    public WebCrawler(URL startUrl) {
        urlsToCrawl.add(startUrl);
    }

    public synchronized void start() {
        exec = new TrackingExecutor(Executors.newCachedThreadPool());
        for (URL url : urlsToCrawl) {
            submitCrawlTask(url);
        }
        urlsToCrawl.clear();
    }

    private void submitCrawlTask(URL url) {
        exec.execute(new CrawlTask(url));
    }

    protected abstract List<URL> processPage(URL url);

    private class CrawlTask implements Runnable {
        private final URL url;

        public CrawlTask(URL url) {
            this.url = url;
        }

        private int count = 1;

        boolean alreadyCrawled() {
            return seen.putIfAbsent(url, true) != null;
        }

        void markUncrawled() {
            seen.remove(url);
            System.out.printf("marking %s uncrawled%n" + url);
        }

        @Override
        public void run() {
            for (URL link : processPage(url)) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                submitCrawlTask(link);
            }

        }

        public URL getPage() {
            return url;
        }
    }


}
