package com.epam.esm.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order extends Entity {
    private User user;
    private Integer cost;
    private LocalDateTime date;
    private List<GiftCertificate> giftCertificateList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<GiftCertificate> getGiftCertificateList() {
        return giftCertificateList;
    }

    public void setGiftCertificateList(List<GiftCertificate> giftCertificateList) {
        this.giftCertificateList = giftCertificateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return user.equals(order.user) &&
                cost.equals(order.cost) &&
                date.equals(order.date) &&
                giftCertificateList.equals(order.giftCertificateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, cost, date, giftCertificateList);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + super.getId() +
                ", user=" + user +
                ", cost=" + cost +
                ", date=" + date +
                ", giftCertificateList=" + giftCertificateList +
                '}';
    }
}
