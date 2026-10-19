package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParcelBox<T extends Parcel> {

    private final int maxWeight;
    private final List<T> parcels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        if (maxWeight < 0) {
            throw new IllegalArgumentException(
                    "Максимальный вес не может быть отрицательным"
            );
        }
        this.maxWeight = maxWeight;
    }

    public boolean addParcel(T parcel) {
        int totalWeight = parcel.getWeight();

        for (T item : parcels) {
            totalWeight += item.getWeight();
        }

        if (totalWeight > maxWeight) {
            System.out.println(
                    "Превышен максимальный вес коробки. Посылка не добавлена."
            );
            return false;
        }

        parcels.add(parcel);
        return true;
    }

    public List<T> getAllParcels() {
        return Collections.unmodifiableList(parcels);
    }
}