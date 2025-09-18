package com.eventostec.api.adapters.inbound.controller;

import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponRequestDTO;
import com.eventostec.api.application.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/coupon")
@Tag(name = "Cupons", description = "Operações relacionadas aos cupons de desconto")
public class CouponController {

    private final CouponService couponService;
    
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping(value = "/event/{eventId}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Criar cupom para evento", description = "Adiciona um novo cupom de desconto a um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cupom criado com sucesso",
                    content = @Content(schema = @Schema(implementation = Coupon.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<Coupon> addCouponsToEvent(
            @Parameter(description = "ID único do evento") @PathVariable UUID eventId, 
            @RequestBody CouponRequestDTO data) {
        Coupon coupons = couponService.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Listar cupons do evento", description = "Retorna todos os cupons de um evento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cupons retornada com sucesso")
    })
    public ResponseEntity<List<Coupon>> getEventCoupons(
            @Parameter(description = "ID único do evento") @PathVariable UUID eventId) {
        List<Coupon> coupons = couponService.getEventCoupons(eventId);
        return ResponseEntity.ok(coupons);
    }

    @DeleteMapping("/{couponId}")
    @Operation(summary = "Deletar cupom", description = "Remove um cupom de desconto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cupom deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<Void> deleteCoupon(
            @Parameter(description = "ID único do cupom") @PathVariable UUID couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.noContent().build();
    }
}
