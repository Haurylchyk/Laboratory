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
public class GiftCertificateCompositeParameter {
    private String name;
    private String description;
    private String tagName;
    private SortType sortType;
    private SortOrder sortOrder;

    public GiftCertificateCompositeParameter() {
    }

    public GiftCertificateCompositeParameter(String tagName, String name, String description, String sortType, String sortOrder) {
        if (tagName != null) {
            setTagName(tagName);
        }
        if (name != null) {
            setName(name);
        }
        if (description != null) {
            setDescription(description);
        }
        if (sortType != null) {
            setSortType(sortType);
        }
        if (sortOrder != null) {
            setSortOrder(sortOrder);
        }
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = SortType.valueOf(sortType.toUpperCase());
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = SortOrder.valueOf(sortOrder.toUpperCase());
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

    public boolean isEmpty() {
        return name == null && description == null && tagName == null && sortType == null && sortOrder == null;
    }
}
