package ch08;

import java.util.Set;

/**
 * @author: ymm
 * @date: 2021/8/3
 * @version: 1.0.0
 * @description: 8-13 表示“搬箱子“之类谜题的抽象类
 */

/**
 *
 * @param <P> 位置类
 * @param <M> 移动类
 */
public interface Puzzle<P, M> {
    /**
     * 返回初始位置
     * @return
     */
    P initialPosition();

    /**
     *
     * @param position
     * @return
     */
    boolean isGoal(P position);

    /**
     *
     * @param position
     * @return
     */
    Set<M> legalMoves(P position);

    /**
     *
     * @param position
     * @param move
     * @return
     */
    P move(P position, M move);
}
