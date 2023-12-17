package com.demo.PoS.controller;

import com.demo.PoS.dto.RestockRequest;
import com.demo.PoS.model.entity.Item;
import com.demo.PoS.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item savedItem = itemService.saveItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable UUID itemId) {
        Item item = itemService.findById(itemId);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable UUID itemId, @RequestBody Item item) {
        Item updatedItem = itemService.updateItem(itemId, item);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID itemId) {
        itemService.deleteById(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{itemId}/restock")
    public ResponseEntity<Item> restockItem(@PathVariable UUID itemId, @RequestBody RestockRequest restockRequest) {
        Item updatedItem = itemService.restockItem(itemId, restockRequest.getStock());
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

}
