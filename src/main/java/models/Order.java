package models;

import java.util.List;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public String getFirstName() {
        return firstName;
    }

    public Order setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Order setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getMetroStation() {
        return metroStation;
    }

    public Order setMetroStation(int metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Order setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public int getRentTime() {
        return rentTime;
    }

    public Order setRentTime(int rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public Order setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Order setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public List<String> getColor() {
        return color;
    }

    public Order setColor(List<String> color) {
        this.color = color;
        return this;
    }
    private static Order baseOrder() {
        return new Order()
                .setFirstName("Test")
                .setLastName("User")
                .setAddress("Moscow, Kremlin")
                .setMetroStation(1)
                .setPhone("+79991112233")
                .setRentTime(3)
                .setDeliveryDate("2024-12-31")
                .setComment("Test order");
    }

    // Заказ с чёрным цветом
    public static Order blackOrder() {
        return baseOrder().setColor(List.of("BLACK"));
    }

    // Заказ с серым цветом
    public static Order greyOrder() {
        return baseOrder().setColor(List.of("GREY"));
    }

    // Заказ с двумя цветами
    public static Order blackAndGreyOrder() {
        return baseOrder().setColor(List.of("BLACK", "GREY"));
    }

    // Заказ без цвета
    public static Order noColorOrder() {
        return baseOrder().setColor(List.of());
    }
}
