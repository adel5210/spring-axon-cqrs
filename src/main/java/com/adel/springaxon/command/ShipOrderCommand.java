package com.adel.springaxon.command;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ShipOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
}
