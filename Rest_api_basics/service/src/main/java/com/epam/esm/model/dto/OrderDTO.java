package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderDTO {
    private Integer id;
    private List<OrderGiftCertificateDTO> giftCertificateList;
    private Integer cost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OrderGiftCertificateDTO> getGiftCertificateList() {
        return giftCertificateList;
    }

    public void setGiftCertificateList(List<OrderGiftCertificateDTO> giftCertificateList) {
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
        OrderDTO orderDTO = (OrderDTO) o;
        return id.equals(orderDTO.id) &&
                giftCertificateList.equals(orderDTO.giftCertificateList) &&
                cost.equals(orderDTO.cost) &&
                date.equals(orderDTO.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, giftCertificateList, cost, date);
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", giftCertificateList=" + giftCertificateList +
                ", cost=" + cost +
                ", date=" + date +
                '}';
    }
}
