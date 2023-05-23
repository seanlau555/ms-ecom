package com.motiv.orderservice.service;

import com.motiv.orderservice.dto.InventoryResponse;
import com.motiv.orderservice.dto.OrderRequest;
import com.motiv.orderservice.mapper.OrderDataMapper;
import com.motiv.orderservice.model.Order;
import com.motiv.orderservice.model.OrderLineItems;
import com.motiv.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    // final = constants variable
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order = orderDataMapper.orderRequestToOrder(orderRequest);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // call inventory service, and place order if product is in stock
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()
                )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .block();

        if (inventoryResponseArray == null){
            throw new IllegalArgumentException("Error from checking inventory.");
        }

        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is no in stock, please try again later.");
        }
    }


}
