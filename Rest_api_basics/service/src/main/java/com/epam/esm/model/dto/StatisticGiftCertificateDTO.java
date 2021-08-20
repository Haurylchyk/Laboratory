package com.epam.esm.model.dto;

import java.util.Objects;

public class StatisticGiftCertificateDTO {
    private Integer maxPrice;
    private Integer maxDuration;

    public StatisticGiftCertificateDTO(Integer maxPrice, Integer maxDuration) {
        this.maxPrice = maxPrice;
        this.maxDuration = maxDuration;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Integer maxDuration) {
        this.maxDuration = maxDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticGiftCertificateDTO that = (StatisticGiftCertificateDTO) o;
        return Objects.equals(maxPrice, that.maxPrice) &&
                Objects.equals(maxDuration, that.maxDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxPrice, maxDuration);
    }
}

