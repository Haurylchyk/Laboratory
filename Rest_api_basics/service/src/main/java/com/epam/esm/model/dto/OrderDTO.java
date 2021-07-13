package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderDTO extends BaseDTO<OrderDTO> {

    private List<OrderGiftCertificateDTO> giftCertificateList;
    private Integer cost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime date;

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
        if (!super.equals(o)) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(giftCertificateList, orderDTO.giftCertificateList) &&
                Objects.equals(cost, orderDTO.cost) &&
                Objects.equals(date, orderDTO.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), giftCertificateList, cost, date);
    }
}
