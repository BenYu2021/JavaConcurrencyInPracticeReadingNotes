package ch06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 6-2 在Web服务器中为每个请求启动一个新线程（不要这么做）
 */
public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);

        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    handRequest(connection);
                }
            };

            new Thread(task).start();
        }
    }

    private static void handRequest(Socket connection) {
        System.out.println("connection = " + connection);
    }
}
