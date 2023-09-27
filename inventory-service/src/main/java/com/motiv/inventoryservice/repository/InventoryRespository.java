package com.motiv.inventoryservice.repository;

import com.motiv.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRespository extends JpaRepository<Inventory, Long> {

  List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
