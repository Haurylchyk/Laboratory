package com.epam.esm.dto;

import java.util.Objects;

public class UserInOrderDTO {
    private Integer id;
    private String name;
    private String login;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
        UserInOrderDTO that = (UserInOrderDTO) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                login.equals(that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login);
    }

    @Override
    public String toString() {
        return "UserInOrderDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
