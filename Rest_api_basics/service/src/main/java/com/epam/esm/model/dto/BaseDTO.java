package com.epam.esm.model.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public abstract class BaseDTO<T extends BaseDTO<T>> extends RepresentationModel<T> {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseDTO baseDTO = (BaseDTO) o;
        return Objects.equals(id, baseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
