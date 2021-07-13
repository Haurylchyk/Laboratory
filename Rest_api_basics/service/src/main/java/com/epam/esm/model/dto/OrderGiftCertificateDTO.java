package com.epam.esm.model.dto;

import java.util.Objects;

public class OrderGiftCertificateDTO {

    private GiftCertificateDTO giftCertificate;

    public GiftCertificateDTO getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificateDTO giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderGiftCertificateDTO that = (OrderGiftCertificateDTO) o;
        return Objects.equals(giftCertificate, that.giftCertificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giftCertificate);
    }
}
