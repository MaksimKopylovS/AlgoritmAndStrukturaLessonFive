package task.two;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskTwo {

    private static final List<Item> ITEMS = new ArrayList<>();
    private static final Map<Integer, Bag> CACHE = new HashMap<>();
    private static final boolean FINITE_ITEMS = true;

    public static void main(String[] args) {
        ITEMS.add(new Item(4, 12, "КОТ"));
        ITEMS.add(new Item(2, 2, "БАТАРЕЯ"));
        ITEMS.add(new Item(2, 1, "КИРПИЧЬ"));
        ITEMS.add(new Item(1, 1, "БОРЩЬ"));
        ITEMS.add(new Item(10, 4, "ШАУРМА"));
        Bag best = bestBagForCapa(15);
        System.out.println(best.toString());
    }

    public static Bag bestBagForCapa(int capa) {
        if (CACHE.containsKey(capa)) return CACHE.get(capa);
        if (capa < 0) return null;
        if (capa == 0) return new Bag(0, 0);

        int currentWeight = -1;
        int currentValue = -1;
        String newItem = null;
        List<String> previousItems = null;
        for (Item p : ITEMS) {
            Bag previous = bestBagForCapa(capa - p.weight);
            if (previous == null) continue;

            int weightSoFar = previous.weight;
            int valueSoFar = previous.value;
            int nextBestValue = 0;
            Item candidateItem = null;
            for (Item candidate : ITEMS) {
                if (FINITE_ITEMS && previous.alreadyHas(candidate)) continue;
                if (weightSoFar + candidate.weight <= capa && nextBestValue < valueSoFar + candidate.value) {
                    candidateItem = candidate;
                    nextBestValue = valueSoFar + candidate.value;
                }
            }

            if (candidateItem != null && nextBestValue > currentValue) {
                currentValue = nextBestValue;
                currentWeight = weightSoFar + candidateItem.weight;
                newItem = candidateItem.name;
                previousItems = previous.contents;
            }
        }

        if (currentWeight <= 0 || currentValue <= 0) throw new RuntimeException("не может быть 0");
        Bag bestBag = new Bag(currentWeight, currentValue);
        bestBag.add(previousItems);
        bestBag.add(Collections.singletonList(newItem));
        CACHE.put(capa, bestBag);
        return bestBag;
    }

}

class Item {

    int value;
    int weight;
    String name;

    Item(int value, int weight, String name) {
        this.value = value;
        this.weight = weight;
        this.name = name;
    }
}

class Bag {

    List<String> contents = new ArrayList<>();
    int weight;
    int value;

    boolean alreadyHas(Item item) {
        return contents.contains(item.name);
    }

    @Override
    public String toString() {
        return "weight " + weight + " , value " + value + "\n" + contents.toString();
    }

    void add(List<String> name) {
        contents.addAll(name);
    }

    Bag(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }

}
