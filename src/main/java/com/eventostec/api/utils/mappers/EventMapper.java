package com.eventostec.api.utils.mappers;

import com.eventostec.api.adapters.outbound.entities.JpaEventEntity;
import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventDetailsDTO;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.domain.event.EventResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "dto.title", target = "title"),
            @Mapping(source = "dto.description", target = "description"),
            @Mapping(target = "imgUrl", source = "imgUrl"),
            @Mapping(source = "dto.eventUrl", target = "eventUrl"),
            @Mapping(source = "dto.date", target = "date", qualifiedByName = "stringToDate"),
            @Mapping(source = "dto.remote", target = "remote")
    })
    Event dtoToEntity(EventRequestDTO dto, String imgUrl);

    @Mappings({
            @Mapping(source = "jpa.id", target = "id"),
            @Mapping(source = "jpa.title", target = "title"),
            @Mapping(source = "jpa.description", target = "description"),
            @Mapping(source = "jpa.eventUrl", target = "eventUrl"),
            @Mapping(source = "jpa.date", target = "date"),
            @Mapping(source = "jpa.remote", target = "remote"),
            @Mapping(source = "jpa.imgUrl", target = "imgUrl")
    })
    Event jpaToDomain(JpaEventEntity jpa);

    @Mappings({
            @Mapping(source = "event.id", target = "id"),
            @Mapping(source = "event.title", target = "title"),
            @Mapping(source = "event.description", target = "description"),
            @Mapping(source = "event.imgUrl", target = "imgUrl"),
            @Mapping(source = "event.eventUrl", target = "eventUrl"),
            @Mapping(source = "event.date", target = "date"),
            @Mapping(target = "city", ignore = true),
            @Mapping(target = "state", ignore = true),
            @Mapping(source = "event.remote", target = "remote")
    })
    EventResponseDTO domainToResponseDto(Event event);

    @Mappings({
            @Mapping(source = "event.id", target = "id"),
            @Mapping(source = "event.title", target = "title"),
            @Mapping(source = "event.description", target = "description"),
            @Mapping(source = "event.imgUrl", target = "imgUrl"),
            @Mapping(source = "event.eventUrl", target = "eventUrl"),
            @Mapping(source = "event.date", target = "date"),
            @Mapping(source = "event.remote", target = "remote")
    })
    JpaEventEntity domainToJpa(Event event);

    default EventDetailsDTO domainToDetailsDto(Event event, Optional<Address> address, List<Coupon> coupons) {
        String city = address.map(Address::getCity).orElse("");
        String uf = address.map(Address::getUf).orElse("");

        List<EventDetailsDTO.CouponDTO> couponDTOs = coupons.stream()
                .map(coupon -> new EventDetailsDTO.CouponDTO(
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.getValid()))
                .collect(Collectors.toList());

        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                city,
                uf,
                event.getImgUrl(),
                event.getEventUrl(),
                couponDTOs);
    }

    @Named("stringToDate")
    default Date stringToDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateString);
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString + ". Expected format: yyyy-MM-ddTHH:mm:ss");
        }
    }

    @Named("longToDate")
    default Date longToDate(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }

    @Named("dateToLong")
    default Long dateToLong(Date date) {
        return date != null ? date.getTime() : null;
    }
}
