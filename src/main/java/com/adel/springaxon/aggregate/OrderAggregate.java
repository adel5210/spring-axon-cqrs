package com.adel.springaxon.aggregate;

import com.adel.springaxon.command.ConfirmOrderCommand;
import com.adel.springaxon.command.CreateOrderCommand;
import com.adel.springaxon.command.ShipOrderCommand;
import com.adel.springaxon.event.OrderConfirmedEvent;
import com.adel.springaxon.event.OrderCreateEvent;
import com.adel.springaxon.event.OrderShippedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;

    private boolean orderConfirmed;

    protected OrderAggregate(){}

    @CommandHandler
    public OrderAggregate(final CreateOrderCommand command){
        AggregateLifecycle.apply(
                new OrderCreateEvent(
                        command.getOrderId(),
                        command.getProductId()
                )
        );
    }

    @CommandHandler
    public void handle(final ConfirmOrderCommand command){
        if(this.orderConfirmed){
            return;
        }

        AggregateLifecycle.apply(
                new OrderConfirmedEvent(this.orderId)
        );
    }

    @CommandHandler
    public void handle(final ShipOrderCommand command){
        if(!this.orderConfirmed){
            throw new RuntimeException("Unconfirmed order");
        }

        AggregateLifecycle.apply(
                new OrderShippedEvent(this.orderId)
        );
    }

    @EventSourcingHandler
    public void on(final OrderCreateEvent event){
        this.orderId = event.getOrderId();
        this.orderConfirmed = false;
    }

    @EventSourcingHandler
    public void on(final OrderConfirmedEvent event){
        this.orderConfirmed = true;
    }


}
