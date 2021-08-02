package ch07;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description:  7-17 通过“毒丸”对象来关闭服务
 */
public class IndexingService {
    public static final int CAPACITY = 1000;
    public static final File POISON = new File("");
    private final BlockingQueue<File> queue;
    private final FileFilter fileFilter;
    private final File root;
    private final IndexerThread consumer = new IndexerThread();
    private final CrawlerThread producer = new CrawlerThread();

    public IndexingService(File root, final FileFilter fileFilter) {
        this.root = root;
        this.queue = new LinkedBlockingDeque<>(CAPACITY);
        this.fileFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || fileFilter.accept(f);
            }
        };
    }

    class CrawlerThread extends Thread {
        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                /* 发生异常*/
            } finally {
                while (true) {
                    try {
                        queue.put(POISON);
                        break;
                    } catch (InterruptedException e) {
                        /* 重新尝试 */
                    }
                }
            }
        }

        private void crawl(File root) throws InterruptedException {
            File[] entries = root.listFiles(fileFilter);
            if (entries != null) {
                for (File entry : entries) {
                    if (entry.isDirectory()) {
                        crawl(entry);
                    } else if (!alreadyIndexed(entry)) {
                        queue.put(entry);
                    }
                }
            }
        }

        private boolean alreadyIndexed(File f) {
            return false;
        }
    }

    class IndexerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    File file = queue.take();
                    if (file == POISON) {
                        break;
                    } else {
                        indexFile(file);
                    }
                }
            } catch (InterruptedException consumed) {

            }
        }

        public void indexFile(File file) {
            /*...*/
        }
    }

    public void start() {
        producer.start();
        consumer.start();
    }

    public void stop() {
        producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        consumer.join(); // Waits for this thread to die.
    }
}
