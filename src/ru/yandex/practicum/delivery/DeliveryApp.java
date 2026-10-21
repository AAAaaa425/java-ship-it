package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>(100);
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(100);
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(100);
    private static final List<Trackable> trackableParcels = new ArrayList<>();

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
                    reportTrackableParcels();
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
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отследить посылки");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Выберите тип посылки:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");

        int type = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите описание посылки:");
        String description = scanner.nextLine();

        System.out.println("Введите вес посылки:");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите адрес доставки:");
        String deliveryAddress = scanner.nextLine();

        System.out.println("Введите день отправки:");
        int sendDay = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1:
                StandardParcel standardParcel = new StandardParcel(description, weight, deliveryAddress, sendDay);

                standardBox.addParcel(standardParcel);
                break;

            case 2:
                FragileParcel fragileParcel = new FragileParcel(description, weight, deliveryAddress, sendDay);

                fragileBox.addParcel(fragileParcel);

                if (fragileBox.containsParcel(fragileParcel)) {
                    trackableParcels.add(fragileParcel);
                }
                break;

            case 3:
                System.out.println("Введите срок хранения в днях:");
                int timeToLive = Integer.parseInt(scanner.nextLine());

                PerishableParcel perishableParcel = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);

                perishableBox.addParcel(perishableParcel);
                break;

            default:
                System.out.println("Неверный тип посылки.");
        }
    }

    private static void sendParcels() {
        for (StandardParcel parcel : standardBox.getAllParcels()) {
            parcel.packageItem();
            parcel.deliver();
        }

        for (FragileParcel parcel : fragileBox.getAllParcels()) {
            parcel.packageItem();
            parcel.deliver();
        }

        for (PerishableParcel parcel : perishableBox.getAllParcels()) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        int totalCost = 0;

        for (StandardParcel parcel : standardBox.getAllParcels()) {
            totalCost += parcel.calculateDeliveryCost();
        }

        for (FragileParcel parcel : fragileBox.getAllParcels()) {
            totalCost += parcel.calculateDeliveryCost();
        }

        for (PerishableParcel parcel : perishableBox.getAllParcels()) {
            totalCost += parcel.calculateDeliveryCost();
        }

        System.out.println("Общая стоимость доставки: " + totalCost);
    }

    private static void reportTrackableParcels() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет посылок для отслеживания.");
            return;
        }

        System.out.println("Введите новое местоположение:");
        String newLocation = scanner.nextLine();

        for (Trackable parcel : trackableParcels) {
            parcel.reportStatus(newLocation);
        }
    }

    private static void showBoxContents() {
        System.out.println("Какую коробку показать?");
        System.out.println("1 — Стандартные посылки");
        System.out.println("2 — Хрупкие посылки");
        System.out.println("3 — Скоропортящиеся посылки");

        int type = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1:
                System.out.println("Стандартные посылки:");
                for (StandardParcel parcel : standardBox.getAllParcels()) {
                    System.out.println(parcel.description);
                }
                break;

            case 2:
                System.out.println("Хрупкие посылки:");
                for (FragileParcel parcel : fragileBox.getAllParcels()) {
                    System.out.println(parcel.description);
                }
                break;

            case 3:
                System.out.println("Скоропортящиеся посылки:");
                for (PerishableParcel parcel : perishableBox.getAllParcels()) {
                    System.out.println(parcel.description);
                }
                break;

            default:
                System.out.println("Неверный тип коробки.");
        }
    }
}