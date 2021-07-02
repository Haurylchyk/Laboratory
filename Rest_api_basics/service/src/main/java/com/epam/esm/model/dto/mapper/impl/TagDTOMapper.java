package com.epam.esm.model.dto.mapper.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.mapper.AbstractMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The class converts Tag to TagDTO and vice versa.
 */
@Component
public class TagDTOMapper extends AbstractMapper<Tag, TagDTO> {

    public TagDTOMapper(ModelMapper modelMapper) {
        super(Tag.class, TagDTO.class, modelMapper);
    }
}
