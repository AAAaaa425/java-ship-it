package ru.yandex.practicum.delivery.parcels;

public class StandardParcel extends Parcel {

    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay, 2);
    }
}
