package com.epam.esm.assembler;

import com.epam.esm.UserController;
import com.epam.esm.model.dto.UserDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDTO,UserDTO> {

    private static final String LINK_NAME_ALL_USERS = "All Users";

    @Override
    public UserDTO toModel(UserDTO userDTO) {
        userDTO.add(
                linkTo(methodOn(UserController.class).findById(userDTO.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).findAll(Pageable.unpaged(), null))
                        .withRel(LINK_NAME_ALL_USERS));
        return userDTO;
    }

    public List<UserDTO> toModel(List<UserDTO> userDTOList) {
        return userDTOList.stream().map(userDTO -> toModel(userDTO)).collect(Collectors.toList());
    }
}
