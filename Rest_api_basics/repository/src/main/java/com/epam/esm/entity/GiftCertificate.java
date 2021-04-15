package com.epam.esm.entity;

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
public class GiftCertificate extends Entity {

    private String name;
    private String description;
    private Integer price;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastsUpdateDate;

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
        return lastsUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastsUpdateDate) {
        this.lastsUpdateDate = lastsUpdateDate;
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
        if (!super.equals(o)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return name.equals(that.name) &&
                description.equals(that.description) &&
                price.equals(that.price) &&
                duration.equals(that.duration) &&
                createDate.equals(that.createDate) &&
                lastsUpdateDate.equals(that.lastsUpdateDate) &&
                tagList.equals(that.tagList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, price, duration, createDate, lastsUpdateDate, tagList);
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
                ", lastsUpdateDate=" + lastsUpdateDate +
                ", tagList=" + tagList +
                '}';
    }
}
