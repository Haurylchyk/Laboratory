package com.epam.esm.dao.query;

import com.epam.esm.dao.query.sort.SortOrder;
import com.epam.esm.dao.query.sort.SortType;

/**
 * Class describes a composite parameter
 * that contains a set of different parameters.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public class GiftCertificateParam {
    private String tagName;
    private String name;
    private String description;
    private SortType sortType;
    private SortOrder sortOrder;

    public GiftCertificateParam() {
    }

    public GiftCertificateParam(String tagName, String name, String description, SortType sortType, SortOrder sortOrder) {
        this.tagName = tagName;
        this.name = name;
        this.description = description;
        this.sortType = sortType;
        this.sortOrder = sortOrder;
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
