package com.epam.esm;

import com.epam.esm.assembler.UserModelAssembler;
import com.epam.esm.model.dto.AuthResponseDTO;
import com.epam.esm.model.dto.SignUpUserDTO;
import com.epam.esm.model.dto.AuthRequestDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.security.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO signUp(@Valid @RequestBody SignUpUserDTO signUpUserDTO) {
        return userModelAssembler.toModel(userService.signUp(signUpUserDTO));
    }

    @PostMapping("/auth")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO authRequestDTO) {
        UserDTO user = userService.login(authRequestDTO);
        return new AuthResponseDTO(user.getId(),user.getLogin(),user.getRole(),
                jwtTokenProvider.createToken(user.getLogin()));
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable Integer id) {
        return userModelAssembler.toModel(userService.finById(id));
    }

    @GetMapping
    public PagedModel<UserDTO> findAll(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<UserDTO> userDTOPage = userService.findAll(pageable);
        userDTOPage.get().forEach(userDTO -> userModelAssembler.toModel(userDTO));
        return assembler.toModel(userDTOPage);
    }
}
