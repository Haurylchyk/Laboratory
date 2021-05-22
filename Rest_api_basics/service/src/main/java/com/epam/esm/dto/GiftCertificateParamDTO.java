package com.epam.esm.dto;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class GiftCertificateParamDTO {

    @Size(min = 1, max = 100)
    private String name;
    @Size(min = 1, max = 255)
    private String description;
    private List<String> priceFilterList;
    private List<String> durationFilterList;
    private List<String> tagNameList;
    private String sortType;
    private String sortOrder;

    public GiftCertificateParamDTO() {
    }

    public GiftCertificateParamDTO(String name, String description, List<String> priceFilterList, List<String> durationFilterList,
                                   List<String> tagNameList, String sortType, String sortOrder) {
        this.name = name;
        this.description = description;
        this.priceFilterList = priceFilterList;
        this.durationFilterList = durationFilterList;
        this.tagNameList = tagNameList;
        this.sortType = sortType;
        this.sortOrder = sortOrder;
    }

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

    public List<String> getPriceFilterList() {
        return priceFilterList;
    }

    public void setPriceFilterList(List<String> priceFilterList) {
        this.priceFilterList = priceFilterList;
    }

    public List<String> getDurationFilterList() {
        return durationFilterList;
    }

    public void setDurationFilterList(List<String> durationFilterList) {
        this.durationFilterList = durationFilterList;
    }

    public List<String> getTagNameList() {
        return tagNameList;
    }

    public void setTagNameList(List<String> tagNameList) {
        this.tagNameList = tagNameList;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificateParamDTO that = (GiftCertificateParamDTO) o;
        return name.equals(that.name) &&
                description.equals(that.description) &&
                priceFilterList.equals(that.priceFilterList) &&
                durationFilterList.equals(that.durationFilterList) &&
                tagNameList.equals(that.tagNameList) &&
                sortType.equals(that.sortType) &&
                sortOrder.equals(that.sortOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, priceFilterList, durationFilterList, tagNameList, sortType, sortOrder);
    }
}
