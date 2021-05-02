package com.epam.esm.dto;

import java.util.List;

public class GiftCertificateParamDTO {

    private String name;
    private String description;
    private List<String> price;
    private List<String> duration;
    private List<String> tagName;
    private String sortType;
    private String sortOrder;
    private String page;
    private String size;

    public GiftCertificateParamDTO() {
    }

    public GiftCertificateParamDTO(String name, String description, List<String> price, List<String> duration,
                                   List<String> tagName, String sortType, String sortOrder, String page, String size) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tagName = tagName;
        this.sortType = sortType;
        this.sortOrder = sortOrder;
        this.page = page;
        this.size = size;
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

    public List<String> getPrice() {
        return price;
    }

    public void setPrice(List<String> price) {
        this.price = price;
    }

    public List<String> getDuration() {
        return duration;
    }

    public void setDuration(List<String> duration) {
        this.duration = duration;
    }

    public List<String> getTagName() {
        return tagName;
    }

    public void setTagName(List<String> tagName) {
        this.tagName = tagName;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
