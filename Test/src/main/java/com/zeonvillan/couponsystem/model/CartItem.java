package com.zeonvillan.couponsystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CartItem {
    
    @NotNull(message = "Product ID is required")
    @JsonProperty("product_id")
    private Long productId;
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
    
    @JsonProperty("total_discount")
    private BigDecimal totalDiscount = BigDecimal.ZERO;
    
    // Constructors
    public CartItem() {}
    
    public CartItem(Long productId, Integer quantity, BigDecimal price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.totalDiscount = BigDecimal.ZERO;
    }
    
    public CartItem(Long productId, Integer quantity, BigDecimal price, BigDecimal totalDiscount) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.totalDiscount = totalDiscount != null ? totalDiscount : BigDecimal.ZERO;
    }
    
    // Business logic methods
    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
    
    public BigDecimal getFinalPrice() {
        return getTotalPrice().subtract(totalDiscount);
    }
    
    public BigDecimal getDiscountedUnitPrice() {
        if (quantity == 0) {
            return price;
        }
        BigDecimal unitDiscount = totalDiscount.divide(BigDecimal.valueOf(quantity), 2, BigDecimal.ROUND_HALF_UP);
        return price.subtract(unitDiscount);
    }
    
    public void addDiscount(BigDecimal discount) {
        if (discount != null && discount.compareTo(BigDecimal.ZERO) > 0) {
            this.totalDiscount = this.totalDiscount.add(discount);
        }
    }
    
    public void setDiscount(BigDecimal discount) {
        this.totalDiscount = discount != null ? discount : BigDecimal.ZERO;
    }
    
    public CartItem copy() {
        return new CartItem(productId, quantity, price, totalDiscount);
    }
    
    // Getters and Setters
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }
    
    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount != null ? totalDiscount : BigDecimal.ZERO;
    }
}
