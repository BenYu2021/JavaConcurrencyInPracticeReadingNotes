package ch04;

import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: ymm
 * @date: 2021/7/30
 * @version: 1.0.0
 * @description:
 */
@ThreadSafe
public class PersonSet {
    private final Set<Person> mySet = new HashSet<>();

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }

    interface Person {

    }
}
