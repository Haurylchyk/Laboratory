package com.epam.esm;

import com.epam.esm.assembler.UserModelAssembler;
import com.epam.esm.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Integer id) {
        return userModelAssembler.toModel(userService.finById(id));
    }

    @GetMapping
    public List<UserDTO> findAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "1") Integer size) {
        return userModelAssembler.toModel(userService.findAll(page, size));
    }
}
