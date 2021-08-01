package ch05;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description: 5-15 通过CyclicBarrier协调细胞自动衍生系统中的计算
 */
public class CellularAutomata {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                mainBoard.commitNewValues();
            }
        });
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }

    }

    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            while (!board.hasConvergence()) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY(); y++) {
                        board.setNewValue(x, y, computeValue(x, y));
                    }
                }

                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    return;
                } catch (BrokenBarrierException e) {
                    return;
                }
            }
        }

        private int computeValue(int x, int y) {
            // 计算(x,y)中的新值
            return 0;
        }
    }

    public void start() {
        for (int i = 0; i < workers.length; i++) {
            new Thread(workers[i]).start();
        }
        mainBoard.waitForConvergence();
    }

    interface Board {
        int getMaxX();

        int getMaxY();

        int getValue(int x, int y);

        int setNewValue(int x, int y, int value);

        void commitNewValues();

        boolean hasConvergence();

        boolean waitForConvergence();

        Board getSubBoard(int numPartitions, int index);
    }
}
