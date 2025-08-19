package com.decolatech.easytravel.domain.bundle.service;

import com.decolatech.easytravel.domain.bundle.dto.LocationDTO;
import com.decolatech.easytravel.domain.bundle.entity.Location;
import com.decolatech.easytravel.domain.bundle.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LocationDTO getLocationById(Integer id) {
        return locationRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Localização não encontrada"));
    }

    public LocationDTO createLocation(LocationDTO locationDTO) {
        if (locationRepository.existsByCountryAndStatesAndCity(
                locationDTO.getCountry(), locationDTO.getStates(), locationDTO.getCity())) {
            throw new RuntimeException("Localização já existe");
        }

        Location location = convertToEntity(locationDTO);
        Location savedLocation = locationRepository.save(location);
        return convertToDTO(savedLocation);
    }

    public LocationDTO updateLocation(Integer id, LocationDTO locationDTO) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Localização não encontrada"));

        boolean updated = false;

        if (locationDTO.getCountry() != null && !existingLocation.getCountry().equals(locationDTO.getCountry())) {
            existingLocation.setCountry(locationDTO.getCountry());
            updated = true;
        }

        if (locationDTO.getStates() != null && !existingLocation.getStates().equals(locationDTO.getStates())) {
            existingLocation.setStates(locationDTO.getStates());
            updated = true;
        }

        if (locationDTO.getCity() != null && !existingLocation.getCity().equals(locationDTO.getCity())) {
            existingLocation.setCity(locationDTO.getCity());
            updated = true;
        }

        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada na localização");
        }

        Location savedLocation = locationRepository.save(existingLocation);
        return convertToDTO(savedLocation);
    }

    public void deleteLocation(Integer id) {
        if (!locationRepository.existsById(id)) {
            throw new RuntimeException("Localização não encontrada");
        }
        locationRepository.deleteById(id);
    }

    public List<LocationDTO> getLocationsByCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("País não pode ser nulo ou vazio");
        }
        return locationRepository.findByCountry(country.trim()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LocationDTO> searchLocationsByCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade não pode ser nula ou vazia");
        }
        return locationRepository.findByCityContainingIgnoreCase(city.trim()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LocationDTO convertToDTO(Location location) {
        LocationDTO dto = new LocationDTO();
        dto.setId(location.getId());
        dto.setCountry(location.getCountry());
        dto.setStates(location.getStates());
        dto.setCity(location.getCity());
        return dto;
    }

    private Location convertToEntity(LocationDTO dto) {
        Location location = new Location();
        location.setId(dto.getId());
        location.setCountry(dto.getCountry());
        location.setStates(dto.getStates());
        location.setCity(dto.getCity());
        return location;
    }
}
