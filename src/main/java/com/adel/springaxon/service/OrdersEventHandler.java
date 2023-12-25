package com.adel.springaxon.service;

import com.adel.springaxon.event.OrderConfirmedEvent;
import com.adel.springaxon.event.OrderCreateEvent;
import com.adel.springaxon.event.OrderShippedEvent;
import com.adel.springaxon.model.Order;
import com.adel.springaxon.query.FindAllOrderedProductsQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersEventHandler {
    private final Map<String, Order> orders = new HashMap<>();

    @EventHandler
    public void on(final OrderCreateEvent event){
        final String orderId = event.getOrderId();
        orders.put(orderId, new Order(orderId, event.getProductId()));
    }

    @EventHandler
    public void on(final OrderConfirmedEvent event){
        final String orderId = event.getOrderId();
        orders.get(orderId).setOrderConfirmed();
    }

    @EventHandler
    public void on(final OrderShippedEvent event){
        final String orderId = event.getOrderId();
        orders.get(orderId).setOrderShipped();
    }

    @QueryHandler
    public List<Order> handle(final FindAllOrderedProductsQuery query){
        return new ArrayList<>(orders.values());
    }
}
