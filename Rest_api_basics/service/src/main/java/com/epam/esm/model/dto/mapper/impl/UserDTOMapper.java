package com.epam.esm.model.dto.mapper.impl;

import com.epam.esm.entity.User;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.dto.mapper.AbstractMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The class converts User to UserDTO and vice versa.
 */
@Component
public class UserDTOMapper extends AbstractMapper<User, UserDTO> {

    public UserDTOMapper(ModelMapper modelMapper) {
        super(User.class, UserDTO.class, modelMapper);
    }
}