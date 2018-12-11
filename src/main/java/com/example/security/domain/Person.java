package com.example.security.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Person {
    private Integer personId;

    private String name;

    private Integer age;

    //0代表女孩，1代表男孩
    private Integer sex;

    private Date birthday;
}
