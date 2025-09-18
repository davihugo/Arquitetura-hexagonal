package com.eventostec.api.domain.coupon;

import com.eventostec.api.adapters.outbound.entities.JpaEventEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue
    private UUID id;

    private String code;
    private Integer discount;
    private Date valid;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private JpaEventEntity event;

    // Explicit getters and setters for MapStruct compatibility
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public Integer getDiscount() { return discount; }
    public void setDiscount(Integer discount) { this.discount = discount; }
    
    public Date getValid() { return valid; }
    public void setValid(Date valid) { this.valid = valid; }
    
    public JpaEventEntity getEvent() { return event; }
    public void setEvent(JpaEventEntity event) { this.event = event; }
}
