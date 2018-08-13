package com.example.nikolina.restaurantapp.database;

public class Cart {

    private String name;
    private String surname;
    private String address;
    private String phone;
    private String order;
    private String price;
    private double lat;
    private double lng;

    public Cart(){
    }

    public Cart(String name, String surname, String address, String phone, String order, String price, double lat, double lng) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.order = order;
        this.price = price;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
