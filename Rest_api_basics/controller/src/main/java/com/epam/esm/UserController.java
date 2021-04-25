package com.epam.esm;

import com.epam.esm.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Integer id) {
        return userService.finById(id);
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }
}
