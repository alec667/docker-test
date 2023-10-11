package com.example.dockerresttest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    private Integer userId;
    private String userName;
    private String userAddress;
}
