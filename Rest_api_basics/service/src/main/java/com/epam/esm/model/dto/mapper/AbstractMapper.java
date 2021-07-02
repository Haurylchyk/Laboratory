package com.epam.esm.model.dto.mapper;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.model.dto.BaseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractMapper<E extends BaseEntity, D extends BaseDTO> implements Mapper<E, D> {

    protected ModelMapper modelMapper;
    private Class<E> entityClass;
    private Class<D> dtoClass;

    public AbstractMapper(Class<E> entityClass, Class<D> dtoClass, ModelMapper modelMapper) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.modelMapper = modelMapper;

    }

    public E convertToEntity(D dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, entityClass);
    }

    public D convertToDTO(E entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, dtoClass);
    }

    public List<D> convertToDTO(List<E> entityList) {
        return entityList.stream().map(order -> convertToDTO(order)).collect(Collectors.toList());
    }
}
