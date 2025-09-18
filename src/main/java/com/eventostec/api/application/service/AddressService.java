package com.eventostec.api.application.service;

import com.eventostec.api.domain.address.Address;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.domain.event.EventRequestDTO;
import com.eventostec.api.adapters.outbound.entities.JpaEventEntity;
import com.eventostec.api.adapters.outbound.repositories.AddressRepository;
import com.eventostec.api.adapters.outbound.repositories.JpaEventRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final JpaEventRepository jpaEventRepository;
    
    public AddressService(AddressRepository addressRepository, JpaEventRepository jpaEventRepository) {
        this.addressRepository = addressRepository;
        this.jpaEventRepository = jpaEventRepository;
    }

    public void createAddress(EventRequestDTO data, Event event) {
        Address address = new Address();
        address.setCity(data.city());
        address.setUf(data.state());
        
        // Get the persisted JpaEventEntity by ID
        JpaEventEntity jpaEvent = jpaEventRepository.findById(event.getId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        address.setEvent(jpaEvent);
        
        addressRepository.save(address);
    }

    public Optional<Address> findByEventId(UUID eventId) {
        return addressRepository.findByEventId(eventId);
    }
}
