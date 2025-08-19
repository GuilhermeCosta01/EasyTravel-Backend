package com.decolatech.easytravel.domain.bundle.service;

import com.decolatech.easytravel.domain.bundle.dto.BundleLocationDTO;
import com.decolatech.easytravel.domain.bundle.dto.LocationDTO;
import com.decolatech.easytravel.domain.bundle.entity.BundleLocation;
import com.decolatech.easytravel.domain.bundle.entity.Bundle;
import com.decolatech.easytravel.domain.bundle.entity.Location;
import com.decolatech.easytravel.domain.bundle.repository.BundleLocationRepository;
import com.decolatech.easytravel.domain.bundle.repository.BundleRepository;
import com.decolatech.easytravel.domain.bundle.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BundleLocationService {

    @Autowired
    private BundleLocationRepository bundleLocationRepository;

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private LocationRepository locationRepository;

    public List<BundleLocationDTO> getAllBundleLocations() {
        return bundleLocationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BundleLocationDTO getBundleLocationById(Integer id) {
        return bundleLocationRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Localização de pacote não encontrada"));
    }

    public BundleLocationDTO createBundleLocation(BundleLocationDTO bundleLocationDTO) {
        if (bundleLocationDTO.getBundleId() == null) {
            throw new RuntimeException("ID do bundle é obrigatório");
        }
        if (bundleLocationDTO.getDepartureId() == null) {
            throw new RuntimeException("ID da localização de partida é obrigatório");
        }
        if (bundleLocationDTO.getDestinationId() == null) {
            throw new RuntimeException("ID da localização de destino é obrigatório");
        }

        Bundle bundle = bundleRepository.findById(bundleLocationDTO.getBundleId())
                .orElseThrow(() -> new RuntimeException("Bundle não encontrado"));

        Location departure = locationRepository.findById(bundleLocationDTO.getDepartureId())
                .orElseThrow(() -> new RuntimeException("Localização de partida não encontrada"));

        Location destination = locationRepository.findById(bundleLocationDTO.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Localização de destino não encontrada"));

        if (departure.getId().equals(destination.getId())) {
            throw new RuntimeException("Partida e destino não podem ser iguais");
        }

        BundleLocation bundleLocation = convertToEntity(bundleLocationDTO);
        bundleLocation.setBundle(bundle);
        bundleLocation.setDeparture(departure);
        bundleLocation.setDestination(destination);

        BundleLocation savedBundleLocation = bundleLocationRepository.save(bundleLocation);
        return convertToDTO(savedBundleLocation);
    }

    public BundleLocationDTO updateBundleLocation(Integer id, BundleLocationDTO bundleLocationDTO) {
        BundleLocation existing = bundleLocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relação pacote-localização não encontrada"));

        boolean updated = false;

        if (bundleLocationDTO.getBundleId() != null && !existing.getBundle().getId().equals(bundleLocationDTO.getBundleId())) {
            Bundle bundle = bundleRepository.findById(bundleLocationDTO.getBundleId())
                    .orElseThrow(() -> new RuntimeException("Bundle não encontrado"));
            existing.setBundle(bundle);
            updated = true;
        }

        if (bundleLocationDTO.getDepartureId() != null && !existing.getDeparture().getId().equals(bundleLocationDTO.getDepartureId())) {
            Location departure = locationRepository.findById(bundleLocationDTO.getDepartureId())
                    .orElseThrow(() -> new RuntimeException("Localização de partida não encontrada"));
            existing.setDeparture(departure);
            updated = true;
        }

        if (bundleLocationDTO.getDestinationId() != null && !existing.getDestination().getId().equals(bundleLocationDTO.getDestinationId())) {
            Location destination = locationRepository.findById(bundleLocationDTO.getDestinationId())
                    .orElseThrow(() -> new RuntimeException("Localização de destino não encontrada"));
            existing.setDestination(destination);
            updated = true;
        }

        // Validar se partida e destino não são iguais após as atualizações
        if (existing.getDeparture().getId().equals(existing.getDestination().getId())) {
            throw new RuntimeException("Partida e destino não podem ser iguais");
        }

        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada na localização do pacote");
        }

        BundleLocation saved = bundleLocationRepository.save(existing);
        return convertToDTO(saved);
    }

    public void deleteBundleLocation(Integer id) {
        if (!bundleLocationRepository.existsById(id)) {
            throw new RuntimeException("Localização de bundle não encontrada");
        }
        bundleLocationRepository.deleteById(id);
    }

    public List<BundleLocationDTO> getBundleLocationsByBundle(Integer bundleId) {
        if (bundleId == null) {
            throw new IllegalArgumentException("ID do bundle não pode ser nulo");
        }
        return bundleLocationRepository.findByBundleId(bundleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BundleLocationDTO> getBundleLocationsByRoute(Integer departureId, Integer destinationId) {
        if (departureId == null) {
            throw new IllegalArgumentException("ID da localização de partida não pode ser nulo");
        }
        if (destinationId == null) {
            throw new IllegalArgumentException("ID da localização de destino não pode ser nulo");
        }
        if (departureId.equals(destinationId)) {
            throw new IllegalArgumentException("Partida e destino não podem ser iguais");
        }
        return bundleLocationRepository.findBundlesByRoute(departureId, destinationId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BundleLocationDTO convertToDTO(BundleLocation bundleLocation) {
        BundleLocationDTO dto = new BundleLocationDTO();
        dto.setId(bundleLocation.getId());
        dto.setBundleId(bundleLocation.getBundle().getId());
        dto.setDepartureId(bundleLocation.getDeparture().getId());
        dto.setDestinationId(bundleLocation.getDestination().getId());

        // Converter localizações para DTOs
        dto.setDeparture(convertLocationToDTO(bundleLocation.getDeparture()));
        dto.setDestination(convertLocationToDTO(bundleLocation.getDestination()));

        return dto;
    }

    private LocationDTO convertLocationToDTO(Location location) {
        LocationDTO dto = new LocationDTO();
        dto.setId(location.getId());
        dto.setCountry(location.getCountry());
        dto.setStates(location.getStates());
        dto.setCity(location.getCity());
        return dto;
    }

    private BundleLocation convertToEntity(BundleLocationDTO dto) {
        BundleLocation bundleLocation = new BundleLocation();
        bundleLocation.setId(dto.getId());
        return bundleLocation;
    }
}
