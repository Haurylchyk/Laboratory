package com.epam.esm.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class CreatingUserData {
    @NotNull
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[A-zА-я_]+$")
    private String name;

    @NotNull
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[A-z0-9_]+$")
    private String login;

    @NotNull
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-z0-9!@#$%^&*]+$")
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatingUserData that = (CreatingUserData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, password);
    }

    @Override
    public String toString() {
        return "CreatingUserData{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
