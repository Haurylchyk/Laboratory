package com.epam.esm.entity;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Audited
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String login;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orderList;

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

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orders) {
        this.orderList = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                login.equals(user.login) &&
                orderList.equals(user.orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, login, orderList);
    }
}
