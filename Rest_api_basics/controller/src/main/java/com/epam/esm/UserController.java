package com.epam.esm;

import com.epam.esm.assembler.UserModelAssembler;
import com.epam.esm.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@Validated
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
    public List<UserDTO> findAll(@RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
                                 @RequestParam(required = false, defaultValue = "1") @Min(1) Integer size) {
        return userModelAssembler.toModel(userService.findAll(page, size));
    }
}
