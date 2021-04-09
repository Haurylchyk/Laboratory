package com.epam.esm.request;

public class GiftCertificateCompositeParameterDTO {
    private String tagName;
    private String name;
    private String description;
    private String sortType;
    private String sortOrder;

    public GiftCertificateCompositeParameterDTO() {
    }

    public GiftCertificateCompositeParameterDTO(String tagName, String name, String description, String sortType, String sortOrder) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
        this.sortType = sortType;
        this.sortType = sortOrder;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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
}
