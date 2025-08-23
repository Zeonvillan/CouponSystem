package com.zeonvillan.couponsystem.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    
    @NotEmpty(message = "Cart items cannot be empty")
    @Valid
    private List<CartItem> items = new ArrayList<>();
    
    // Constructors
    public Cart() {}
    
    public Cart(List<CartItem> items) {
        this.items = items != null ? items : new ArrayList<>();
    }
    
    // Business logic methods
    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public int getTotalQuantity() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    
    public CartItem getItemByProductId(Long productId) {
        return items.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }
    
    public List<CartItem> getItemsByProductIds(List<Long> productIds) {
        return items.stream()
                .filter(item -> productIds.contains(item.getProductId()))
                .toList();
    }
    
    public int getQuantityByProductId(Long productId) {
        CartItem item = getItemByProductId(productId);
        return item != null ? item.getQuantity() : 0;
    }
    
    public int getTotalQuantityByProductIds(List<Long> productIds) {
        return items.stream()
                .filter(item -> productIds.contains(item.getProductId()))
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
    
    public boolean hasProduct(Long productId) {
        return items.stream()
                .anyMatch(item -> item.getProductId().equals(productId));
    }
    
    public boolean hasAnyProduct(List<Long> productIds) {
        return items.stream()
                .anyMatch(item -> productIds.contains(item.getProductId()));
    }
    
    public void addItem(CartItem item) {
        CartItem existingItem = getItemByProductId(item.getProductId());
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            items.add(item);
        }
    }
    
    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
    }
    
    public Cart copy() {
        List<CartItem> copiedItems = items.stream()
                .map(CartItem::copy)
                .toList();
        return new Cart(new ArrayList<>(copiedItems));
    }
    
    // Getters and Setters
    public List<CartItem> getItems() {
        return items;
    }
    
    public void setItems(List<CartItem> items) {
        this.items = items != null ? items : new ArrayList<>();
    }
}
