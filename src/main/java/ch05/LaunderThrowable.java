package ch05;

/**
 * @author: ymm
 * @date: 2021/8/1
 * @version: 1.0.0
 * @description:
 *  Launder 洗熨(衣物); 洗(钱);
 */
public class LaunderThrowable {

    /**
     * 如果Throwable是Error，那么抛出它；如果是RuntimeException，那么返回它，否则抛出IllegalStateException
     *
     * @param t
     * @return
     */
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            throw (Error) t;
        } else {
            throw new IllegalStateException("Not unchecked", t);
        }
    }
}
