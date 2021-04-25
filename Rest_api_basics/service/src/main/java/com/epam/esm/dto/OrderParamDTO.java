package com.epam.esm.dto;

import java.util.List;

public class OrderParamDTO {
    private Integer user;
    private List<Integer> certificates;

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public List<Integer> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Integer> certificates) {
        this.certificates = certificates;
    }
}
