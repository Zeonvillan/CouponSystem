package com.zeonvillan.couponsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("cart-wise")
public class CartWiseCoupon extends Coupon {
    
    @NotNull(message = "Threshold is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Threshold must be greater than 0")
    @Column(name = "threshold", precision = 10, scale = 2)
    private BigDecimal threshold;
    
    @NotNull(message = "Discount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount must be greater than 0")
    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType = DiscountType.PERCENTAGE;
    
    public enum DiscountType {
        PERCENTAGE, FIXED_AMOUNT
    }
    
    @Override
    public String getType() {
        return "cart-wise";
    }
    
    // Getters and Setters
    public BigDecimal getThreshold() {
        return threshold;
    }
    
    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }
    
    public BigDecimal getDiscount() {
        return discount;
    }
    
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    
    public DiscountType getDiscountType() {
        return discountType;
    }
    
    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}
