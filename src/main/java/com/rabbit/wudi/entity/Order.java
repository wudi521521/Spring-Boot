package com.rabbit.wudi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Dillon Wu
 * @Title: Order
 * @Description: 实体(生产端的与消费端共用的实体)
 * @date 2019/10/17 16:58
 */
@Data
public class Order implements Serializable {

    private String id;

    private String name;

    public Order(){}

    public Order(String id,String name){
        this.id=id;
        this.name=name;
    }
}
