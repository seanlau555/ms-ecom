package com.motiv.orderservice.mapper;

import com.motiv.orderservice.dto.OrderLineItemsDto;
import com.motiv.orderservice.dto.OrderRequest;
import com.motiv.orderservice.model.Order;
import com.motiv.orderservice.model.OrderLineItems;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDataMapper {
  public Order orderRequestToOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());
    List<OrderLineItems> orderLineItems = getOrderLineItemsFromRequest(orderRequest);
    order.setOrderLineItemsList(orderLineItems);
    return order;
  }

  private List<OrderLineItems> getOrderLineItemsFromRequest(OrderRequest orderRequest){
    return orderRequest.getOrderLineItemsDtoList()
            .stream()
//                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
            .map(this::mapToDto)
            .toList();
  }

  private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
    //        orderLineItems.setPrice(orderLineItemsDto.getPrice());
//        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
//        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    return OrderLineItems.builder()
            .price(orderLineItemsDto.getPrice())
            .quantity(orderLineItemsDto.getQuantity())
            .skuCode(orderLineItemsDto.getSkuCode())
            .build();
  }
}
