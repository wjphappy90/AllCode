package com.example.mybatisplusdemo.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author IT讲坛
 * @since 2019-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TestUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String account;


}
