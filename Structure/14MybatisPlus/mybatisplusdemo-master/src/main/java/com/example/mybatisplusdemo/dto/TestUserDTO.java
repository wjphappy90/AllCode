package com.example.mybatisplusdemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @package: com.example.mybatisplusdemo.dto
 * @description: dto
 * @author: IT讲坛
 * @create: 2019-01-08 10:15
 **/
@Data
public class TestUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String account;


}
