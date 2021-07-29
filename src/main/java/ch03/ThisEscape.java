package ch03;

/**
 * @author: ymm
 * @date: 2021/7/29
 * @version: 1.0.0
 * @description: 3-7 隐式地使用this引用逃逸（不要这么做）
 */
public class ThisEscape {

    public ThisEscape(EventSource source) {
        /**
         * ThisEscape发布EventListener时，也隐含发布了ThisEscape实例本身，
         * 因为在这个内部类实例中包含了对ThisEscape实例的隐含引用。
         */
        source.registerListener(new EventListener() {
            @Override
            public void onEvent(Event e) {
                doSomeThing(e);
            }
        });
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
