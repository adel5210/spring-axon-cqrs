package com.adel.springaxon.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
    private final String productId;
}
