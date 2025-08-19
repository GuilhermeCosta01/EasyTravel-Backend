package com.decolatech.easytravel.domain.bundle.service;

import com.decolatech.easytravel.domain.bundle.dto.BundleDTO;
import com.decolatech.easytravel.domain.bundle.entity.Bundle;
import com.decolatech.easytravel.domain.bundle.enums.BundleRank;
import com.decolatech.easytravel.domain.bundle.enums.BundleStatus;
import com.decolatech.easytravel.domain.bundle.repository.BundleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BundleService {

    @Autowired
    private BundleRepository bundleRepository;

    public List<BundleDTO> getAllBundles() {
        return bundleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BundleDTO getBundleById(Integer id) {
        return bundleRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
    }

    public BundleDTO createBundle(BundleDTO bundleDTO) {
        Bundle bundle = convertToEntity(bundleDTO);
        Bundle savedBundle = bundleRepository.save(bundle);
        return convertToDTO(savedBundle);
    }

    public BundleDTO updateBundle(Integer id, BundleDTO bundleDTO) {
        Bundle existingBundle = bundleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));

        boolean updated = false;

        if (bundleDTO.getBundleTitle() != null && !existingBundle.getBundleTitle().equals(bundleDTO.getBundleTitle())) {
            existingBundle.setBundleTitle(bundleDTO.getBundleTitle());
            updated = true;
        }

        if (bundleDTO.getBundleDescription() != null && !existingBundle.getBundleDescription().equals(bundleDTO.getBundleDescription())) {
            existingBundle.setBundleDescription(bundleDTO.getBundleDescription());
            updated = true;
        }

        if (bundleDTO.getInitialPrice() != null && !existingBundle.getInitialPrice().equals(bundleDTO.getInitialPrice())) {
            existingBundle.setInitialPrice(bundleDTO.getInitialPrice());
            updated = true;
        }

        if (bundleDTO.getBundleRank() != null && !existingBundle.getBundleRank().equals(bundleDTO.getBundleRank())) {
            existingBundle.setBundleRank(bundleDTO.getBundleRank());
            updated = true;
        }

        if (bundleDTO.getInitialDate() != null && !existingBundle.getInitialDate().equals(bundleDTO.getInitialDate())) {
            existingBundle.setInitialDate(bundleDTO.getInitialDate());
            updated = true;
        }

        if (bundleDTO.getFinalDate() != null && !existingBundle.getFinalDate().equals(bundleDTO.getFinalDate())) {
            existingBundle.setFinalDate(bundleDTO.getFinalDate());
            updated = true;
        }

        if (bundleDTO.getQuantity() != null && !existingBundle.getQuantity().equals(bundleDTO.getQuantity())) {
            existingBundle.setQuantity(bundleDTO.getQuantity());
            updated = true;
        }

        if (bundleDTO.getTravelersNumber() != null && !existingBundle.getTravelersNumber().equals(bundleDTO.getTravelersNumber())) {
            existingBundle.setTravelersNumber(bundleDTO.getTravelersNumber());
            updated = true;
        }

        if (bundleDTO.getBundleStatus() != null && !existingBundle.getBundleStatus().equals(bundleDTO.getBundleStatus())) {
            existingBundle.setBundleStatus(bundleDTO.getBundleStatus());
            updated = true;
        }

        if (!updated) {
            throw new IllegalArgumentException("Nenhuma alteração foi detectada no pacote");
        }

        Bundle savedBundle = bundleRepository.save(existingBundle);
        return convertToDTO(savedBundle);
    }

    public void deleteBundle(Integer id) {
        if (!bundleRepository.existsById(id)) {
            throw new RuntimeException("Pacote não encontrado");
        }
        bundleRepository.deleteById(id);
    }

    public List<BundleDTO> getBundlesByStatus(BundleStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
        return bundleRepository.findByBundleStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BundleDTO> getBundlesByRank(BundleRank rank) {
        if (rank == null) {
            throw new IllegalArgumentException("Rank não pode ser nulo");
        }
        return bundleRepository.findByBundleRank(rank).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BundleDTO> searchBundlesByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio");
        }
        return bundleRepository.findByBundleTitleContainingIgnoreCase(title.trim()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BundleDTO> getAvailableBundles() {
        return bundleRepository.findAvailableBundles(BundleStatus.AVAILABLE).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BundleDTO> getBundlesByPriceRange(Double minPrice, Double maxPrice) {
        if (minPrice == null || maxPrice == null) {
            throw new IllegalArgumentException("Preço mínimo e máximo não podem ser nulos");
        }
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Preços não podem ser negativos");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Preço mínimo não pode ser maior que o preço máximo");
        }
        return bundleRepository.findByPriceRange(minPrice, maxPrice).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

//    public List<BundleDTO> getAllBundlesByUser(Integer userId) {
//        return bundleRepository.getAllBundlesByUser(userId)
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }

    private BundleDTO convertToDTO(Bundle bundle) {
        BundleDTO dto = new BundleDTO();
        dto.setId(bundle.getId());
        dto.setBundleTitle(bundle.getBundleTitle());
        dto.setBundleDescription(bundle.getBundleDescription());
        dto.setInitialPrice(bundle.getInitialPrice());
        dto.setBundleRank(bundle.getBundleRank());
        dto.setInitialDate(bundle.getInitialDate());
        dto.setFinalDate(bundle.getFinalDate());
        dto.setQuantity(bundle.getQuantity());
        dto.setTravelersNumber(bundle.getTravelersNumber());
        dto.setBundleStatus(bundle.getBundleStatus());
        return dto;
    }

    private Bundle convertToEntity(BundleDTO dto) {
        Bundle bundle = new Bundle();
        bundle.setId(dto.getId());
        bundle.setBundleTitle(dto.getBundleTitle());
        bundle.setBundleDescription(dto.getBundleDescription());
        bundle.setInitialPrice(dto.getInitialPrice());
        bundle.setBundleRank(dto.getBundleRank());
        bundle.setInitialDate(dto.getInitialDate());
        bundle.setFinalDate(dto.getFinalDate());
        bundle.setQuantity(dto.getQuantity());
        bundle.setTravelersNumber(dto.getTravelersNumber());
        bundle.setBundleStatus(dto.getBundleStatus() != null ? dto.getBundleStatus() : BundleStatus.AVAILABLE);
        return bundle;
    }
}
