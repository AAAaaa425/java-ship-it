package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);

    private static final List<Parcel> allParcels = new ArrayList<>();
    private static final List<Trackable> trackableParcels = new ArrayList<>();

    private static final ParcelBox<StandardParcel> standardBox =
            new ParcelBox<>(1000);
    private static final ParcelBox<FragileParcel> fragileBox =
            new ParcelBox<>(1000);
    private static final ParcelBox<PerishableParcel> perishableBox =
            new ParcelBox<>(1000);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    reportStatuses();
                    break;
                case 5:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }

        System.out.println("Программа завершена.");
    }

    private static void showMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Обновить местоположение посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Выберите тип посылки:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");

        int type = Integer.parseInt(scanner.nextLine());

        if (type < 1 || type > 3) {
            System.out.println("Неверный тип посылки.");
            return;
        }

        System.out.println("Укажите описание, вес, адрес и день отправки, " + "каждое значение с новой строки:");

        String description = scanner.nextLine();
        int weight = Integer.parseInt(scanner.nextLine());
        String deliveryAddress = scanner.nextLine();
        int sendDay = Integer.parseInt(scanner.nextLine());

        if (weight <= 0 || sendDay < 1 || sendDay > 31) {
            System.out.println("Некорректный вес или день отправки.");
            return;
        }

        Parcel parcel;

        switch (type) {
            case 1:
                StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);

                if (!addToBox(standardBox, standardParcel)) {
                    return;
                }

                parcel = standardParcel;
                break;

            case 2:
                FragileParcel fragileParcel = new FragileParcel(
                        description, weight, deliveryAddress, sendDay);

                if (!addToBox(fragileBox, fragileParcel)) {
                    return;
                }

                trackableParcels.add(fragileParcel);
                parcel = fragileParcel;
                break;

            case 3:
                System.out.println("Укажите срок хранения в днях:");

                int timeToLive = Integer.parseInt(scanner.nextLine());

                if (timeToLive < 0) {
                    System.out.println("Срок хранения не может быть отрицательным.");
                    return;
                }

                PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);

                if (!addToBox(perishableBox, perishableParcel)) {
                    return;
                }

                parcel = perishableParcel;
                break;

            default:
                return;
        }

        allParcels.add(parcel);
        System.out.println("Посылка успешно добавлена.");
    }

    private static <T extends Parcel> boolean addToBox(
            ParcelBox<T> box, T parcel) {

        int currentWeight = 0;

        for (T item : box.getAllParcels()) {
            currentWeight += item.getWeight();
        }

        if (currentWeight + parcel.getWeight() > 1000) {
            System.out.println("Превышен максимальный вес коробки. " + "Посылка не добавлена.");
            return false;
        }

        box.addParcel(parcel);
        return true;
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки.");
            return;
        }

        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        int totalCost = 0;

        for (Parcel parcel : allParcels) {
            totalCost += parcel.calculateDeliveryCost();
        }

        System.out.println("Общая стоимость доставки: " + totalCost);
    }

    private static void reportStatuses() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет посылок с поддержкой трекинга.");
            return;
        }

        System.out.println("Введите новое местоположение:");
        String newLocation = scanner.nextLine();

        for (Trackable parcel : trackableParcels) {
            parcel.reportStatus(newLocation);
        }
    }

    private static void showBoxContents() {
        System.out.println("Выберите коробку:");
        System.out.println("1 — Стандартные посылки");
        System.out.println("2 — Хрупкие посылки");
        System.out.println("3 — Скоропортящиеся посылки");

        int type = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1:
                printParcels(standardBox.getAllParcels());
                break;
            case 2:
                printParcels(fragileBox.getAllParcels());
                break;
            case 3:
                printParcels(perishableBox.getAllParcels());
                break;
            default:
                System.out.println("Неверный тип коробки.");
        }
    }

    private static void printParcels(
            List<? extends Parcel> parcels) {

        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста.");
            return;
        }

        System.out.println("Содержимое коробки:");

        for (Parcel parcel : parcels) {
            System.out.println("Описание: " + parcel.getDescription()
                            + ", вес: " + parcel.getWeight()
                            + ", адрес: " + parcel.getDeliveryAddress()
                            + ", день отправки: " + parcel.getSendDay());
        }
    }
}