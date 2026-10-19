package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.*;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryCostTest {

    @Test
    void shouldCalculateStandardParcelCost() {
        StandardParcel parcel = new StandardParcel("Книга", 5, "Москва", 1);

        assertEquals(10, parcel.calculateDeliveryCost());
    }

    @Test
    void shouldCalculateFragileParcelCost() {
        FragileParcel parcel = new FragileParcel("Стекло", 3, "Казань", 1);

        assertEquals(12, parcel.calculateDeliveryCost());
    }

    @Test
    void shouldCalculatePerishableParcelCost() {
        PerishableParcel parcel = new PerishableParcel("Продукты", 4, "Минск", 1, 3);

        assertEquals(12, parcel.calculateDeliveryCost());
    }

    @Test
    void shouldNotBeExpiredOnLastValidDay() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 2, "Вильнюс", 10, 3);

        assertFalse(parcel.isExpired(13));
    }

    @Test
    void shouldBeExpiredAfterLastValidDay() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 2, "Вильнюс", 10, 3);

        assertTrue(parcel.isExpired(14));
    }

    @Test
    void shouldAddParcelWhenWeightIsBelowLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel parcel = new StandardParcel("Книга", 5, "Москва", 1);

        box.addParcel(parcel);

        assertEquals(1, box.getAllParcels().size());
    }

    @Test
    void shouldNotAddParcelWhenWeightExceedsLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);

        box.addParcel(new StandardParcel("Книга", 7, "Москва", 1));
        box.addParcel(new StandardParcel("Журнал", 5, "Минск", 2));

        assertEquals(1, box.getAllParcels().size());
    }

    @Test
    void shouldAddParcelWhenWeightEqualsLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel parcel = new StandardParcel("Книга", 10, "Москва", 1);

        box.addParcel(parcel);

        assertEquals(1, box.getAllParcels().size());
    }
}