package com.demo.PoS.service;

import com.demo.PoS.exceptions.InvalidRestockOperationException;
import com.demo.PoS.exceptions.ItemNotFoundException;
import com.demo.PoS.model.entity.Item;
import com.demo.PoS.model.enums.ItemType;
import com.demo.PoS.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item findById(UUID itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id: " + itemId));
    }

    public Item updateItem(UUID itemId, Item itemDetails) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id: " + itemId));

        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        item.setPrice(itemDetails.getPrice());
        item.setItemType(itemDetails.getItemType());
        item.setStock(itemDetails.getStock());

        return itemRepository.save(item);
    }

    public void deleteById(UUID itemId) {
        itemRepository.deleteById(itemId);
    }

    public Item restockItem(UUID itemId, Integer newStock) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id: " + itemId));

        if (item.getItemType() != ItemType.PRODUCT) {
            throw new InvalidRestockOperationException("Restock operation is not valid for item type: " + item.getItemType());
        }
        item.setStock(newStock);
        return itemRepository.save(item);
    }

}
