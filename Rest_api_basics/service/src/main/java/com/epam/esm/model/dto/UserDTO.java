package com.epam.esm.model.dto;

import java.util.Objects;

public class UserDTO extends BaseDTO<UserDTO> {
    private String name;
    private String login;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(name, userDTO.name) &&
                Objects.equals(login, userDTO.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, login);
    }
}
