package ru.yandex.practicum.delivery.parcels;

public class PerishableParcel extends Parcel {

    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay, 3);
        this.timeToLive = timeToLive;
    }

    public boolean isExpired(int currentDay) {
        return currentDay > sendDay + timeToLive;
    }
}