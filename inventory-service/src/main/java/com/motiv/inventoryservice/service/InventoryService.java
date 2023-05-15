package com.motiv.inventoryservice.service;

import com.motiv.inventoryservice.repository.InventoryRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryRespository inventoryRespository;

  @Transactional(readOnly = true)
  public boolean isInStock(String skuCode){
    return inventoryRespository.findBySkuCode(skuCode).isPresent();
  }
}
