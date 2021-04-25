package com.epam.esm.entity;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "GIFT_ORDER")
@Audited
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer cost;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ORDER_CERTIFICATE",
            joinColumns = @JoinColumn(name = "ORDER_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "CERT_ID", nullable = false))
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
