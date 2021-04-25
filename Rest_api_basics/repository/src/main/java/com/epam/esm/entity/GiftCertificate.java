package com.epam.esm.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Class contains the main information about
 * GiftCertificate entity.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @see LocalDateTime
 * @since JDK 1.8
 */
@Entity
@Table(name = "GIFT_CERTIFICATE")
@Audited
@DynamicUpdate
public class GiftCertificate extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer duration;

    @Column(name = "CREATE_DATE", insertable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;

    @UniqueElements
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "CERTIFICATE_TAG",
            joinColumns = {@JoinColumn(name = "CERT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "TAG_ID", nullable = false)})
    private List<Tag> tagList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastsUpdateDate) {
        this.lastUpdateDate = lastsUpdateDate;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return name.equals(that.name) &&
                description.equals(that.description) &&
                price.equals(that.price) &&
                duration.equals(that.duration) &&
                createDate.equals(that.createDate) &&
                lastUpdateDate.equals(that.lastUpdateDate) &&
                tagList.equals(that.tagList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, price, duration, createDate, lastUpdateDate, tagList);
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + super.getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastsUpdateDate=" + lastUpdateDate +
                ", tagList=" + tagList +
                '}';
    }
}
