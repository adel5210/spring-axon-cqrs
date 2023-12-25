package com.adel.springaxon.model;

import com.adel.springaxon.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Order {

    private final String orderId;
    private final String productId;
    private OrderStatus orderStatus;

    public Order(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderStatus = OrderStatus.CREATED;
    }

    public void setOrderConfirmed(){
        this.orderStatus = OrderStatus.CONFIRMED;
    }

    public void setOrderShipped(){
        this.orderStatus = OrderStatus.SHIPPED;
    }
}
