package corejava.tasks.xmlparsing.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Flowers {
    private List<Flower> flowers;

    public Flowers() {
        flowers = new ArrayList<>();
    }

    public void add(Flower flower) {
        flowers.add(flower);
    }

    public List<Flower> toList() {
        return Collections.unmodifiableList(flowers);
    }

    public void sortByFlowerName() {
        flowers.sort(Comparator.comparing(f -> f.getDefaultProperties().name));
    }

    public void sortByVolumeOfWatering() {
        flowers.sort(Comparator.comparing(f -> f.getGrowingTips().watering));
    }

    public void sortByAverageLenOfFlower() {
        flowers.sort(Comparator.comparing(f -> f.getVisualParameters().aveLenFlower));
    }
}
