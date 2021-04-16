package com.epam.esm.entity;

import java.util.List;
import java.util.Objects;

public class User extends Entity {

    private String name;
    private List<Order> orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                orders.equals(user.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, orders);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                ", name='" + name + '\'' +
                ", orders=" + orders +
                '}';
    }
}
