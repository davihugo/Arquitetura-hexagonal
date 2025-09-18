package com.eventostec.api.application.service;

import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponRequestDTO;
import com.eventostec.api.adapters.outbound.entities.JpaEventEntity;
import com.eventostec.api.adapters.outbound.repositories.CouponRepository;
import com.eventostec.api.adapters.outbound.repositories.JpaEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final JpaEventRepository jpaEventRepository;
    
    public CouponService(CouponRepository couponRepository, JpaEventRepository jpaEventRepository) {
        this.couponRepository = couponRepository;
        this.jpaEventRepository = jpaEventRepository;
    }

    @Transactional
    public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO couponData) {
        JpaEventEntity event = jpaEventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Coupon coupon = new Coupon();
        coupon.setCode(couponData.code());
        coupon.setDiscount(couponData.discount());
        coupon.setValid(new Date(couponData.valid()));
        coupon.setEvent(event);

        return couponRepository.save(coupon);
    }

    public List<Coupon> consultCoupons(UUID eventId, Date currentDate) {
        return couponRepository.findByEventIdAndValidAfter(eventId, currentDate);
    }

    public List<Coupon> getEventCoupons(UUID eventId) {
        return couponRepository.findByEventId(eventId);
    }

    @Transactional
    public void deleteCoupon(UUID couponId) {
        if (!couponRepository.existsById(couponId)) {
            throw new IllegalArgumentException("Coupon not found");
        }
        couponRepository.deleteById(couponId);
    }
}
