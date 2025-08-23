package com.zeonvillan.couponsystem.dto;

import com.zeonvillan.couponsystem.model.Cart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ApplicableCouponsRequest {
    
    @NotNull(message = "Cart is required")
    @Valid
    private Cart cart;
    
    // Constructors
    public ApplicableCouponsRequest() {}
    
    public ApplicableCouponsRequest(Cart cart) {
        this.cart = cart;
    }
    
    // Getters and Setters
    public Cart getCart() {
        return cart;
    }
    
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
