package ch08;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: ymm
 * @date: 2021/8/3
 * @version: 1.0.0
 * @description: 8-18 在解决器中找不到解答
 */
public class PuzzleSolver<P, M> extends ConcurrentPuzzleSolver<P, M> {

    public PuzzleSolver(Puzzle<P, M> puzzle) {
        super(puzzle);
    }

    private final AtomicInteger taskCount = new AtomicInteger(0);

    protected Runnable newTask(P p, M m, PuzzleNode<P, M> n) {
        return new CountingSolveTask(p, m, n);
    }

    class CountingSolveTask extends SolveTask {
        public CountingSolveTask(P pos, M move, PuzzleNode<P, M> prev) {
            super(pos, move, prev);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                if (taskCount.decrementAndGet() == 0) {
                    solution.setValue(null);
                }
            }
        }
    }
}
