package ch05;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description:
 */
public class Preloader {

    private final FutureTask<ProductInfo> future = new FutureTask<>(new Callable<ProductInfo>() {
        @Override
        public ProductInfo call() throws Exception {
            return loadProductInfo();
        }
    });

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public ProductInfo get() throws InterruptedException, DataLoadException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException) {
                throw (DataLoadException) cause;
            } else {
                throw LaunderThrowable.launderThrowable(cause);
            }
        }
    }

    ProductInfo loadProductInfo() throws DataLoadException {
        return null;
    }


    interface ProductInfo {

    }

}

class DataLoadException extends Exception {

}