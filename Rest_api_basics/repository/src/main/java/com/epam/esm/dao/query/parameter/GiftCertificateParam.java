package com.epam.esm.dao.query.parameter;

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
    private List<String> tagNameList;
    private List<Filter> priceFilterList;
    private List<Filter> durationFilterList;
    private SortType sortType;
    private SortOrder sortOrder;

    public GiftCertificateParam() {
    }

    public GiftCertificateParam(String name, String description, List<Filter> priceFilterList, List<Filter> durationFilterList,
                                List<String> tagNameList, SortType sortType, SortOrder sortOrder) {
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

    public List<String> getTagNameList() {
        return tagNameList;
    }

    public void setTagNameList(List<String> tagNameList) {
        this.tagNameList = tagNameList;
    }

    public List<Filter> getPriceFilterList() {
        return priceFilterList;
    }

    public void setPriceFilterList(List<Filter> priceFilterList) {
        this.priceFilterList = priceFilterList;
    }

    public List<Filter> getDurationFilterList() {
        return durationFilterList;
    }

    public void setDurationFilterList(List<Filter> durationFilterList) {
        this.durationFilterList = durationFilterList;
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
