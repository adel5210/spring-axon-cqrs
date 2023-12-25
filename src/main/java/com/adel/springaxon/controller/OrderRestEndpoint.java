package com.adel.springaxon.controller;

import com.adel.springaxon.command.ConfirmOrderCommand;
import com.adel.springaxon.command.CreateOrderCommand;
import com.adel.springaxon.command.ShipOrderCommand;
import com.adel.springaxon.model.Order;
import com.adel.springaxon.query.FindAllOrderedProductsQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class OrderRestEndpoint {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping("/ship-order")
    public CompletableFuture<Void> shipOrder() {
        final String orderId = UUID.randomUUID().toString();
        return commandGateway.send(
                    new CreateOrderCommand(orderId, "Coffee table")
                ).thenCompose(res -> commandGateway.send(
                    new ConfirmOrderCommand(orderId)
                )).thenCompose(res -> commandGateway.send(
                    new ShipOrderCommand(orderId)
                ));
    }

    @GetMapping("/all-orders")
    public CompletableFuture<List<Order>> findAllOrders(){
        return queryGateway.query(
                new FindAllOrderedProductsQuery(),
                ResponseTypes.multipleInstancesOf(Order.class)
        );
    }
}
