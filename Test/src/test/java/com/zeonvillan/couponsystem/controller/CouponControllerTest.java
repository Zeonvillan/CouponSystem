package com.zeonvillan.couponsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeonvillan.couponsystem.dto.ApplicableCouponsRequest;
import com.zeonvillan.couponsystem.dto.ApplyCouponRequest;
import com.zeonvillan.couponsystem.entity.CartWiseCoupon;
import com.zeonvillan.couponsystem.model.Cart;
import com.zeonvillan.couponsystem.model.CartItem;
import com.zeonvillan.couponsystem.service.CouponService;
import com.zeonvillan.couponsystem.controller.CouponController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CouponController.class)
@org.springframework.test.context.ActiveProfiles("test")
class CouponControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CouponService couponService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private CartWiseCoupon testCoupon;
    private Cart testCart;
    
    @BeforeEach
    void setUp() {
        testCoupon = new CartWiseCoupon();
        testCoupon.setId(1L);
        testCoupon.setThreshold(new BigDecimal("100"));
        testCoupon.setDiscount(new BigDecimal("10"));
        testCoupon.setDiscountType(CartWiseCoupon.DiscountType.PERCENTAGE);
        testCoupon.setDescription("10% off on carts over Rs. 100");
        testCoupon.setIsActive(true);
        
        testCart = new Cart();
        testCart.setItems(Arrays.asList(
            new CartItem(1L, 6, new BigDecimal("50")),
            new CartItem(2L, 3, new BigDecimal("30")),
            new CartItem(3L, 2, new BigDecimal("25"))
        ));
    }
    
    @Test
    void testCreateCoupon_Success() throws Exception {
        // Given if
        when(couponService.createCoupon(any())).thenReturn(testCoupon);
        
        // When & Then
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCoupon)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("cart-wise"))
                .andExpect(jsonPath("$.threshold").value(100))
                .andExpect(jsonPath("$.discount").value(10));
    }
    
    @Test
    void testGetAllCoupons_Success() throws Exception {
        // Given
        List<CartWiseCoupon> coupons = Arrays.asList(testCoupon);
        when(couponService.getAllCoupons()).thenReturn(Arrays.asList(testCoupon));
        
        // When & Then
        mockMvc.perform(get("/api/coupons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].type").value("cart-wise"));
    }
    
    @Test
    void testGetCouponById_Success() throws Exception {
        // Given
        when(couponService.getCouponById(1L)).thenReturn(testCoupon);
        
        // When & Then
        mockMvc.perform(get("/api/coupons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("cart-wise"));
    }
    
    @Test
    void testUpdateCoupon_Success() throws Exception {
        // Given
        when(couponService.updateCoupon(eq(1L), any())).thenReturn(testCoupon);
        
        // When & Then
        mockMvc.perform(put("/api/coupons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCoupon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
    
    @Test
    void testDeleteCoupon_Success() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/coupons/1"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void testGetApplicableCoupons_Success() throws Exception {
        // Given
        ApplicableCouponsRequest request = new ApplicableCouponsRequest(testCart);
        when(couponService.findApplicableCoupons(any())).thenReturn(Arrays.asList());
        
        // When & Then
        mockMvc.perform(post("/api/applicable-coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicable_coupons").isArray());
    }
    
    @Test
    void testApplyCoupon_Success() throws Exception {
        // Given
        ApplyCouponRequest request = new ApplyCouponRequest(testCart);
        when(couponService.applyCoupon(eq(1L), any())).thenReturn(null);
        
        // When & Then
        mockMvc.perform(post("/api/apply-coupon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
    
    @Test
    void testCreateCoupon_InvalidInput() throws Exception {
        // Given - invalid coupon (missing required fields)
        CartWiseCoupon invalidCoupon = new CartWiseCoupon();
        
        // When & Then
        mockMvc.perform(post("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCoupon)))
                .andExpect(status().isBadRequest());
    }
}
