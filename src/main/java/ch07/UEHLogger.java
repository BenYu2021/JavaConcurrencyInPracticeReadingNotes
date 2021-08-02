package ch07;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 7-25 将异常写入日志的UncaughtExceptionHandler
 */
public class UEHLogger implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "Thread terminated with exception: " + t.getName());
    }

}
