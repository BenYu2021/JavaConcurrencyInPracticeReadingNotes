package ch03;

/**
 * @author: ymm
 * @date: 2021/7/29
 * @version: 1.0.0
 * @description: 3-8 使用工厂方法来防止this引用在构造过程中逸出
 */
public class SafeListener {
    private final EventListener listener;

    public SafeListener() {
        listener = new EventListener() {
            @Override
            public void onEvent(Event e) {
                doSomeThing(e);
            }
        };
    }

    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }

    void doSomeThing(Event e) {

    }

    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {

    }

}
