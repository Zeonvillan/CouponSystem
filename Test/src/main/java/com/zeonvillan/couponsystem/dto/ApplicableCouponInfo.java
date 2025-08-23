package com.zeonvillan.couponsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class ApplicableCouponInfo {
    
    @JsonProperty("coupon_id")
    private Long couponId;
    
    private String type;
    
    private BigDecimal discount;
    
    // Constructors
    public ApplicableCouponInfo() {}
    
    public ApplicableCouponInfo(Long couponId, String type, BigDecimal discount) {
        this.couponId = couponId;
        this.type = type;
        this.discount = discount;
    }
    
    // Getters and Setters
    public Long getCouponId() {
        return couponId;
    }
    
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public BigDecimal getDiscount() {
        return discount;
    }
    
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
