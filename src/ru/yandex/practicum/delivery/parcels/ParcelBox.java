package ru.yandex.practicum.delivery.parcels;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {

    private int currentWeight = 0;
    private final int maxWeight;
    private final List<T> parcels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void addParcel(T parcel) {

        if (currentWeight + parcel.weight > maxWeight) {
            System.out.println("Коробка переполнена! Посылка не добавлена.");
            return;
        }

        currentWeight = currentWeight + parcel.weight;
        parcels.add(parcel);
        System.out.println("Посылка добавлена в коробку.");
    }

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);
    }

    public boolean containsParcel(T parcel) {
        return parcels.contains(parcel);
    }
}
