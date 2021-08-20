package com.epam.esm.model.dto;

import com.epam.esm.entity.field.Role;

import java.util.Objects;

public class AuthResponseDTO {
    private Integer id;
    private String login;
    private Role role;
    private String token;

    public AuthResponseDTO(Integer id, String login, Role role, String token) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponseDTO that = (AuthResponseDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(login, that.login) &&
                role == that.role &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, role, token);
    }
}

