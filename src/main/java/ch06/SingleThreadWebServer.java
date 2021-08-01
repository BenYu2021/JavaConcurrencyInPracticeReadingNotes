package ch06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 6-1 串行的Web服务器
 */
public class SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            handleRequest(connection);
        }
    }

    private static void handleRequest(Socket connection) {
        System.out.println("connection.getInetAddress() = " + connection.getInetAddress());
    }
}
