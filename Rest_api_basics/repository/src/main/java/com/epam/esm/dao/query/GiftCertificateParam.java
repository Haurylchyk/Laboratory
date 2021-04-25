package com.epam.esm.dao.query;

import com.epam.esm.dao.query.filter.Filter;
import com.epam.esm.dao.query.sort.SortOrder;
import com.epam.esm.dao.query.sort.SortType;

import java.util.List;

/**
 * Class describes a composite parameter
 * that contains a set of different parameters.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class GiftCertificateParam {

    private String name;
    private String description;
    private List<String> tagName;
    private List<Filter> price;
    private List<Filter> duration;
    private SortType sortType;
    private SortOrder sortOrder;

    public GiftCertificateParam() {
    }

    public GiftCertificateParam( String name, String description, List<Filter> price, List<Filter> duration,
                                 List<String> tagName, SortType sortType, SortOrder sortOrder) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tagName = tagName;
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

    public List<String> getTagName() {
        return tagName;
    }

    public void setTagName(List<String> tagName) {
        this.tagName = tagName;
    }

    public List<Filter> getPrice() {
        return price;
    }

    public void setPrice(List<Filter> price) {
        this.price = price;
    }

    public List<Filter> getDuration() {
        return duration;
    }

    public void setDuration(List<Filter> duration) {
        this.duration = duration;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
