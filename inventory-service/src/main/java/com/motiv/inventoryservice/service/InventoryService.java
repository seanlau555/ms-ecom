package com.motiv.inventoryservice.service;

import com.motiv.inventoryservice.dto.InventoryResponse;
import com.motiv.inventoryservice.repository.InventoryRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryRespository inventoryRespository;

  @Transactional(readOnly = true)
  public List<InventoryResponse> isInStock(List<String> skuCode){
    return inventoryRespository.findBySkuCodeIn(skuCode).stream()
            .map(inventory ->
              InventoryResponse.builder()
                      .skuCode(inventory.getSkuCode())
                      .isInStock(inventory.getQuantity() > 0)
                      .build()
            ).toList();

  }
}
