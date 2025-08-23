package com.zeonvillan.couponsystem.controller;

import com.zeonvillan.couponsystem.dto.*;
import com.zeonvillan.couponsystem.entity.Coupon;
import com.zeonvillan.couponsystem.service.CouponService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CouponController {
    
    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);
    
    @Autowired
    private CouponService couponService;
    
    /**
     * Create a new coupon
     * POST /coupons
     */
    @PostMapping("/coupons")
    public ResponseEntity<Coupon> createCoupon(@Valid @RequestBody Coupon coupon) {
        logger.info("Creating new coupon of type: {}", coupon.getType());
        try {
            Coupon createdCoupon = couponService.createCoupon(coupon);
            return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating coupon: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Retrieve all coupons
     * GET /coupons
     */
    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        logger.info("Fetching all coupons");
        try {
            List<Coupon> coupons = couponService.getAllCoupons();
            return ResponseEntity.ok(coupons);
        } catch (Exception e) {
            logger.error("Error fetching coupons: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Retrieve a specific coupon by its ID
     * GET /coupons/{id}
     */
    @GetMapping("/coupons/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        logger.info("Fetching coupon with ID: {}", id);
        try {
            Coupon coupon = couponService.getCouponById(id);
            return ResponseEntity.ok(coupon);
        } catch (Exception e) {
            logger.error("Error fetching coupon {}: {}", id, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Update a specific coupon by its ID
     * PUT /coupons/{id}
     */
    @PutMapping("/coupons/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @Valid @RequestBody Coupon coupon) {
        logger.info("Updating coupon with ID: {}", id);
        try {
            Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
            return ResponseEntity.ok(updatedCoupon);
        } catch (Exception e) {
            logger.error("Error updating coupon {}: {}", id, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Delete a specific coupon by its ID
     * DELETE /coupons/{id}
     */
    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        logger.info("Deleting coupon with ID: {}", id);
        try {
            couponService.deleteCoupon(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting coupon {}: {}", id, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Fetch all applicable coupons for a given cart and calculate the total discount
     * POST /applicable-coupons
     */
    @PostMapping("/applicable-coupons")
    public ResponseEntity<ApplicableCouponsResponse> getApplicableCoupons(
            @Valid @RequestBody ApplicableCouponsRequest request) {
        logger.info("Finding applicable coupons for cart with {} items", 
                request.getCart().getItems().size());
        try {
            List<ApplicableCouponInfo> applicableCoupons = couponService
                    .findApplicableCoupons(request.getCart());
            ApplicableCouponsResponse response = new ApplicableCouponsResponse(applicableCoupons);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error finding applicable coupons: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Apply a specific coupon to the cart and return the updated cart with discounted prices
     * POST /apply-coupon/{id}
     */
    @PostMapping("/apply-coupon/{id}")
    public ResponseEntity<ApplyCouponResponse> applyCoupon(
            @PathVariable Long id, 
            @Valid @RequestBody ApplyCouponRequest request) {
        logger.info("Applying coupon {} to cart with {} items", 
                id, request.getCart().getItems().size());
        try {
            ApplyCouponResponse response = couponService.applyCoupon(id, request.getCart());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error applying coupon {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
