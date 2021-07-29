package ch03;

import java.util.*;

/**
 * @author: ymm
 * @date: 2021/7/29
 * @version: 1.0.0
 * @description: 3-9 基本类型的局部变量与引用变量的线程封闭
 */
public class Animals {
    Ark ark;
    Species species;
    Gender gender;

    /**
     * @param candidates 被认定适合者
     * @return
     */
    public int loadTheArk(Collection<Animal> candidates) {
        SortedSet<Animal> animals;
        int numParis = 0;
        Animal candidate = null;

        // animals被封闭在方法中，不要让它逸出
        animals = new TreeSet<Animal>(new SpeciesGenderComparator());
        animals.addAll(candidates);
        for (Animal a : animals) {
            if (candidate == null || !candidate.isPotentialMate(a)) {
                candidate = a;
            } else {
                ark.load(new AnimalPair(candidate, a));
                ++numParis;
                candidate = null;
            }
        }
        return numParis;
    }

    // 动物
    class Animal {
        Species species;
        Gender gender;

        // 是否是可能的情侣，判断条件：同物种，不同性别
        public boolean isPotentialMate(Animal other) {
            return species == other.species && gender != other.gender;
        }
    }

    // 物种
    enum Species {
        AARDVARK, BENGAL_TIGER, CARIBOU, DINGO, ELEPHANT, FROG, GNU, HYENA,
        IGUANA, JAGUAR, KIWI, LEOPARD, MASTADON, NEWT, OCTOPUS,
        PIRANHA, QUETZAL, RHINOCEROS, SALAMANDER, THREE_TOED_SLOTH,
        UNICORN, VIPER, WEREWOLF, XANTHUS_HUMMINBIRD, YAK, ZEBRA
    }

    // 性别
    enum Gender {
        MALE, FEMALE
    }

    // 动物对
    class AnimalPair {
        private final Animal one, two;

        public AnimalPair(Animal one, Animal two) {
            this.one = one;
            this.two = two;
        }
    }

    // 比较器：先比较物种再比较性别
    class SpeciesGenderComparator implements Comparator<Animal> {
        public int compare(Animal one, Animal two) {
            int speciesCompare = one.species.compareTo(two.species);
            return (speciesCompare != 0)
                    ? speciesCompare
                    : one.gender.compareTo(two.gender);
        }
    }


    // 装动物的容器
    class Ark {
        private final Set<AnimalPair> loadedAnimals = new HashSet<AnimalPair>();

        public void load(AnimalPair pair) {
            loadedAnimals.add(pair);
        }
    }
}
