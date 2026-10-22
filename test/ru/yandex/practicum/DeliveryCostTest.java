package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.parcels.FragileParcel;
import ru.yandex.practicum.delivery.parcels.ParcelBox;
import ru.yandex.practicum.delivery.parcels.PerishableParcel;
import ru.yandex.practicum.delivery.parcels.StandardParcel;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryCostTest {

    // Проверка стоимости стандартной посылки
    @Test
    void standardParcelDeliveryCost() {
        StandardParcel parcel = new StandardParcel("Книга", 5, "Москва", 10);

        assertEquals(10, parcel.calculateDeliveryCost());
    }

    // Проверка стоимости хрупкой посылки
    @Test
    void fragileParcelDeliveryCost() {
        FragileParcel parcel = new FragileParcel("Стекло", 3, "Минск", 10);

        assertEquals(12, parcel.calculateDeliveryCost());
    }

    // Проверка стоимости скоропортящейся посылки
    @Test
    void perishableParcelDeliveryCost() {
        PerishableParcel parcel = new PerishableParcel("Продукты", 4, "Вильнюс", 10, 3);

        assertEquals(12, parcel.calculateDeliveryCost());
    }

    // Посылка ещё не испортилась
    @Test
    void perishableParcelNotExpiredBeforeDeadline() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 2, "Каунас", 10, 5);

        assertFalse(parcel.isExpired(14));
    }

    // Граничный случай: последний день срока хранения
    @Test
    void perishableParcelNotExpiredOnLastDay() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 2, "Каунас", 10, 5);

        assertFalse(parcel.isExpired(15));
    }

    // Посылка испортилась после окончания срока хранения
    @Test
    void perishableParcelExpiredAfterDeadline() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 2, "Каунас", 10, 5);

        assertTrue(parcel.isExpired(16));
    }

    // Посылка добавляется, если вес не превышает ограничение
    @Test
    void addParcelWhenWeightIsAllowed() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);

        StandardParcel parcel = new StandardParcel("Книга", 5, "Москва", 10);

        box.addParcel(parcel);

        assertEquals(1, box.getAllParcels().size());
        assertTrue(box.containsParcel(parcel));
    }

    // Граничный случай: вес ровно равен ограничению
    @Test
    void addParcelWhenWeightEqualsLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);

        StandardParcel parcel = new StandardParcel("Книга", 10, "Москва", 10);

        box.addParcel(parcel);

        assertEquals(1, box.getAllParcels().size());
    }

    // Посылка не добавляется, если превышен максимальный вес
    @Test
    void doNotAddParcelWhenWeightExceedsLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);

        StandardParcel parcel1 = new StandardParcel("Книга", 6, "Москва", 10);
        StandardParcel parcel2 = new StandardParcel("Альбом", 5, "Минск", 11);

        box.addParcel(parcel1);
        box.addParcel(parcel2);

        assertEquals(1, box.getAllParcels().size());
        assertFalse(box.containsParcel(parcel2));
    }
}