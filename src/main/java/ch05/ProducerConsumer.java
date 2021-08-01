package ch05;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: ymm
 * @date: 2021/7/31
 * @version: 1.0.0
 * @description: 5-8 桌面搜索应用程序中生产者任务和消费者任务
 */
public class ProducerConsumer {
    static public class FileCrawler implements Runnable {
        private final BlockingQueue<File> fileQueue;
        private final FileFilter fileFilter;
        private final File root;

        public FileCrawler(BlockingQueue<File> fileQueue, final FileFilter fileFilter, File root) {
            this.fileQueue = fileQueue;
            this.fileFilter = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || fileFilter.accept(f);
                }
            };
            this.root = root;
        }

        private boolean alreadyIndexed(File f) {
            return false;
        }

        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void crawl(File root) throws InterruptedException {
            File[] entries = root.listFiles(fileFilter);
            if (entries != null) {
                for (File entry : entries) {
                    if (entry.isDirectory()) {
                        crawl(entry);
                    } else if (!alreadyIndexed(entry)) {
                        fileQueue.put(entry);
                    }
                }
            }
        }
    }

    public static class Indexer implements Runnable {
        private final BlockingQueue<File> queue;

        public Indexer(BlockingQueue<File> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    indexFile(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void indexFile(File file) {
            // 索引文件
        }
    }

    private static final int BOUND = 10;
    private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();

    public static void startIndexing(File[] roots) {
        LinkedBlockingQueue<File> queue = new LinkedBlockingQueue<>(BOUND);
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };

        for (File root : roots) {
            new Thread(new FileCrawler(queue, filter, root)).start();
        }

        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }
}

