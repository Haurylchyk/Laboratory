package com.epam.esm.assembler;

import com.epam.esm.UserController;
import com.epam.esm.UserService;
import com.epam.esm.constant.PaginationConstant;
import com.epam.esm.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDTO,UserDTO> {

    private static final String LINK_NAME_ALL_USERS_FIRST = "All Users - FirstPage";
    private static final String LINK_NAME_ALL_USERS_LAST = "All Users - LastPage";
    private Integer size = PaginationConstant.DEFAULT_NUMBER_ON_PAGE;
    private Integer page = PaginationConstant.DEFAULT_PAGE;

    private final UserService userService;

    @Autowired
    public UserModelAssembler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDTO toModel(UserDTO userDTO) {
        userDTO.add(
                linkTo(methodOn(UserController.class).findById(userDTO.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).findAll(page, size))
                        .withRel(LINK_NAME_ALL_USERS_FIRST),
                linkTo(methodOn(UserController.class).findAll(userService.findNumberPagesForAllUsers(size), size))
                        .withRel(LINK_NAME_ALL_USERS_LAST));
        return userDTO;
    }

    public List<UserDTO> toModel(List<UserDTO> userDTOList) {
        return userDTOList.stream().map(userDTO -> toModel(userDTO)).collect(Collectors.toList());
    }
}
