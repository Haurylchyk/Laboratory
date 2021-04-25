package com.epam.esm.dto;

import java.util.List;
import java.util.Objects;

public class UserDTO {

    private Integer id;
    private String name;
    private String login;

    private List<OrderInUserDTO> orderList;

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

    public List<OrderInUserDTO> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderInUserDTO> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id.equals(userDTO.id) &&
                name.equals(userDTO.name) &&
                login.equals(userDTO.login) &&
                orderList.equals(userDTO.orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, orderList);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", orderList=" + orderList +
                '}';
    }
}
