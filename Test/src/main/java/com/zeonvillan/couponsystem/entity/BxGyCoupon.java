package com.zeonvillan.couponsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("bxgy")
public class BxGyCoupon extends Coupon {
    
    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BuyProduct> buyProducts = new ArrayList<>();
    
    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<GetProduct> getProducts = new ArrayList<>();
    
    @NotNull(message = "Repetition limit is required")
    @Min(value = 1, message = "Repetition limit must be at least 1")
    @Column(name = "repetition_limit")
    private Integer repetitionLimit;
    
    @Override
    public String getType() {
        return "bxgy";
    }
    
    // Getters and Setters
    public List<BuyProduct> getBuyProducts() {
        return buyProducts;
    }
    
    public void setBuyProducts(List<BuyProduct> buyProducts) {
        this.buyProducts.clear();
        if (buyProducts != null) {
            this.buyProducts.addAll(buyProducts);
            buyProducts.forEach(product -> product.setCoupon(this));
        }
    }
    
    public List<GetProduct> getGetProducts() {
        return getProducts;
    }
    
    public void setGetProducts(List<GetProduct> getProducts) {
        this.getProducts.clear();
        if (getProducts != null) {
            this.getProducts.addAll(getProducts);
            getProducts.forEach(product -> product.setCoupon(this));
        }
    }
    
    public Integer getRepetitionLimit() {
        return repetitionLimit;
    }
    
    public void setRepetitionLimit(Integer repetitionLimit) {
        this.repetitionLimit = repetitionLimit;
    }
    
    // Helper methods
    public void addBuyProduct(BuyProduct buyProduct) {
        buyProducts.add(buyProduct);
        buyProduct.setCoupon(this);
    }
    
    public void addGetProduct(GetProduct getProduct) {
        getProducts.add(getProduct);
        getProduct.setCoupon(this);
    }
}
