package com.epam.esm.model.dto.mapper;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.model.dto.BaseDTO;

import java.util.List;

/**
 * The Interface for converting Entity to DTO and vice versa.
 */
public interface Mapper<E extends BaseEntity, D extends BaseDTO> {

    E convertToEntity(D dto);

    D convertToDTO(E entity);

    List<D> convertToDTO(List<E> entityList);
}
