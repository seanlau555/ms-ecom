package com.motiv.inventoryservice.controller;

import com.motiv.inventoryservice.repository.InventoryRespository;
import com.motiv.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// Starting with Spring version 4.3,
// the single bean constructor does not
// need to be annotated with the @Autowired annotation.

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

  private final InventoryService inventoryService;

  @Autowired
  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
  }



  @GetMapping("/{sku-code}")
  @ResponseStatus(HttpStatus.OK)
  public boolean isInStock(@PathVariable("sku-code") String skuCode){
    return inventoryService.isInStock(skuCode);
  }
}
