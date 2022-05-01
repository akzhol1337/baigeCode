package com.example.baigecode.business.service;

import com.example.baigecode.business.entity.Organization;
import com.example.baigecode.persistance.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Optional<Organization> getOrganizationById(Long id) {
        return organizationRepository.findById(id);
    }
}
