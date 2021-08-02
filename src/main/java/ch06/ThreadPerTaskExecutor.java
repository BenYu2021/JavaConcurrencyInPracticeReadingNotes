package ch06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 6-5 为每个请求启动一个新线程
 */
public class ThreadPerTaskExecutor implements Executor {

    @Override
    public void execute(Runnable r) {
        new Thread(r).start();
    }

}
