package com.zeonvillan.couponsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeonvillan.couponsystem.model.Cart;
import java.math.BigDecimal;

public class ApplyCouponResponse {
    
    @JsonProperty("updated_cart")
    private Cart updatedCart;
    
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    
    @JsonProperty("total_discount")
    private BigDecimal totalDiscount;
    
    @JsonProperty("final_price")
    private BigDecimal finalPrice;
    
    // Constructors
    public ApplyCouponResponse() {}
    
    public ApplyCouponResponse(Cart updatedCart, BigDecimal totalPrice, BigDecimal totalDiscount, BigDecimal finalPrice) {
        this.updatedCart = updatedCart;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.finalPrice = finalPrice;
    }
    
    // Getters and Setters
    public Cart getUpdatedCart() {
        return updatedCart;
    }
    
    public void setUpdatedCart(Cart updatedCart) {
        this.updatedCart = updatedCart;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }
    
    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }
    
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }
    
    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}
