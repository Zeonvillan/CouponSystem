package com.zeonvillan.couponsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ApplicableCouponsResponse {
    
    @JsonProperty("applicable_coupons")
    private List<ApplicableCouponInfo> applicableCoupons;
    
    // Constructors
    public ApplicableCouponsResponse() {}
    
    public ApplicableCouponsResponse(List<ApplicableCouponInfo> applicableCoupons) {
        this.applicableCoupons = applicableCoupons;
    }
    
    // Getters and Setters
    public List<ApplicableCouponInfo> getApplicableCoupons() {
        return applicableCoupons;
    }
    
    public void setApplicableCoupons(List<ApplicableCouponInfo> applicableCoupons) {
        this.applicableCoupons = applicableCoupons;
    }
}
