package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderInUserDTO {

    private Integer id;
    private List<GiftCertificateDTO> giftCertificateList;
    private Integer cost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<GiftCertificateDTO> getGiftCertificateList() {
        return giftCertificateList;
    }

    public void setGiftCertificateList(List<GiftCertificateDTO> giftCertificateList) {
        this.giftCertificateList = giftCertificateList;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderInUserDTO that = (OrderInUserDTO) o;
        return id.equals(that.id) &&
                giftCertificateList.equals(that.giftCertificateList) &&
                cost.equals(that.cost) &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, giftCertificateList, cost, date);
    }

    @Override
    public String toString() {
        return "UserOrderDTO{" +
                "id=" + id +
                ", giftCertificateList=" + giftCertificateList +
                ", cost=" + cost +
                ", date=" + date +
                '}';
    }
}
