package com.epam.esm.entity;

import org.hibernate.envers.Audited;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "gift_order")
@Audited
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer cost;

    @Column(nullable = false)
    private LocalDateTime date;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderGiftCertificate> giftCertificateList;

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

    public List<OrderGiftCertificate> getGiftCertificateList() {
        return giftCertificateList;
    }

    public void setGiftCertificateList(List<OrderGiftCertificate> giftCertificateList) {
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
