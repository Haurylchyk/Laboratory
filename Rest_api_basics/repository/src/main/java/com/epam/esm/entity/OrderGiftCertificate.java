package com.epam.esm.entity;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "order_certificate")
@Audited
public class OrderGiftCertificate {

    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "order_id")
        protected Integer orderId;
        @Column(name = "cert_id")
        protected Integer certId;

        public Id() {
        }

        public Id(Integer orderId, Integer certId) {
            this.orderId = orderId;
            this.certId = certId;
        }

        public boolean equals(Object o) {
            if (o instanceof Id) {
                Id that = (Id) o;
                return this.orderId.equals(that.orderId)
                        && this.certId.equals(that.certId);
            }
            return false;
        }

        public int hashCode() {
            return orderId.hashCode() + certId.hashCode();
        }
    }

    @EmbeddedId
    protected Id id = new Id();

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "cert_id", insertable = false, updatable = false)
    private GiftCertificate giftCertificate;

    public OrderGiftCertificate() {
    }

    public OrderGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }
}
