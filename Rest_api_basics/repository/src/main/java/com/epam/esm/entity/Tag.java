package com.epam.esm.entity;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Class contains the main information about
 * Tag entity.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Entity
@Audited
public class Tag extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "CERTIFICATE_TAG",
            joinColumns = {@JoinColumn(name = "TAG_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "CERT_ID", nullable = false)})
    private List<GiftCertificate> certificates;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GiftCertificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<GiftCertificate> certificates) {
        this.certificates = certificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + super.getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
