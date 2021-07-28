package ch03;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: ymm
 * @date: 2021/7/28
 * @version: 1.0.0
 * @description: 3-5 发布一个对象
 */
public class Secrets {
    public static Set<Secret> knownSecrets;

    public void initialized() {
        knownSecrets = new HashSet<>();
    }

}

class Secret {

}