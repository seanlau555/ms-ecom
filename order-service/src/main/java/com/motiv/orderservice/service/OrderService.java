package com.motiv.orderservice.service;

import com.motiv.orderservice.dto.OrderLineItemsDto;
import com.motiv.orderservice.dto.OrderRequest;
import com.motiv.orderservice.mapper.OrderDataMapper;
import com.motiv.orderservice.model.Order;
import com.motiv.orderservice.model.OrderLineItems;
import com.motiv.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
// DI
//@RequiredArgsConstructor
@Transactional
public class OrderService {
    // final = constants variable
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDataMapper orderDataMapper;

//    @Autowired
//    public OrderService(OrderRepository orderRepository){
//        this.orderRepository = orderRepository;
//    }

    public void placeOrder(OrderRequest orderRequest){
        Order order = orderDataMapper.orderRequestToOrder(orderRequest);
        orderRepository.save(order);
    }


}
