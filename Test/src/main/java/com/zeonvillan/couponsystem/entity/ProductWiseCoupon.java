package com.zeonvillan.couponsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("product-wise")
public class ProductWiseCoupon extends Coupon {
    
    @NotNull(message = "Product ID is required")
    @Column(name = "product_id")
    private Long productId;
    
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
        return "product-wise";
    }
    
    // Getters and Setters
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
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
