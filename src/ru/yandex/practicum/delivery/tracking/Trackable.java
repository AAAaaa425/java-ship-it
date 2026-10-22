package ru.yandex.practicum.delivery.tracking;

public interface Trackable {
    void reportStatus(String newLocation);
}