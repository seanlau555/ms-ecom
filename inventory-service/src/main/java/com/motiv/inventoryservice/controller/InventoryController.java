package com.motiv.inventoryservice.controller;

import com.motiv.inventoryservice.dto.InventoryResponse;
import com.motiv.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Starting with Spring version 4.3,
// the single bean constructor does not
// need to be annotated with the @Autowired annotation.

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

  private final InventoryService inventoryService;

  // http:/localhost:8003/api/inventory/iphone-13,iphone-12
  // http:/localhost:8003/api/inventory?sku-code=iphone-13&sku-code=iphone-12
  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
    return inventoryService.isInStock(skuCode);
  }
}
