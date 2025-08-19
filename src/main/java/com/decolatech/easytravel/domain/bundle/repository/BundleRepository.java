package com.decolatech.easytravel.domain.bundle.repository;

import com.decolatech.easytravel.domain.bundle.entity.Bundle;
import com.decolatech.easytravel.domain.bundle.enums.BundleRank;
import com.decolatech.easytravel.domain.bundle.enums.BundleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BundleRepository extends JpaRepository<Bundle, Integer> {

    // Buscar por status
    List<Bundle> findByBundleStatus(BundleStatus bundleStatus);

    // Buscar por rank
    List<Bundle> findByBundleRank(BundleRank bundleRank);

    // Buscar por título
    List<Bundle> findByBundleTitleContainingIgnoreCase(String title);

    // Buscar pacotes disponíveis
    @Query("SELECT b FROM Bundle b WHERE b.bundleStatus = :status AND b.quantity > 0")
    List<Bundle> findAvailableBundles(@Param("status") BundleStatus status);

    // Buscar pacotes por faixa de preço
    @Query("SELECT b FROM Bundle b WHERE b.initialPrice BETWEEN :minPrice AND :maxPrice")
    List<Bundle> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    // Buscar pacotes por data
    @Query("SELECT b FROM Bundle b WHERE b.initialDate >= :startDate AND b.finalDate <= :endDate")
    List<Bundle> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Buscar pacotes por número de viajantes
    List<Bundle> findByTravelersNumber(Integer travelersNumber);

    // Buscar pacotes ordenados por preço
    List<Bundle> findAllByOrderByInitialPriceAsc();
    List<Bundle> findAllByOrderByInitialPriceDesc();

    // Faturamento por pacote (corrigido para nativeQuery)
    @Query(value = "SELECT B.ID AS pacoteId, SUM(P.TOT_PRICE) AS faturamento " +
           "FROM TB_BUNDLES B " +
           "INNER JOIN TB_RESERVATIONS R ON B.ID = R.ID_BUNDLE " +
           "INNER JOIN TB_PAYMENTS P ON R.ID = P.ID_RESERVATION " +
           "GROUP BY B.ID", nativeQuery = true)
    List<Object[]> findFaturamentoPorPacote();

    // Vendas por cidade de destino (corrigido para nativeQuery)
    @Query(value = "SELECT L.CITY AS destination, SUM(P.TOT_PRICE) AS totalVendas " +
           "FROM TB_PAYMENTS P " +
           "INNER JOIN TB_RESERVATIONS R ON P.ID_RESERVATION = R.ID " +
           "INNER JOIN TB_BUNDLES B ON R.ID_BUNDLE = B.ID " +
           "INNER JOIN TB_BUNDLE_LOCATIONS BL ON B.ID = BL.ID_BUNDLE " +
           "INNER JOIN TB_LOCATIONS L ON BL.ID_DESTINATION = L.ID " +
           "GROUP BY L.CITY " +
           "ORDER BY totalVendas DESC", nativeQuery = true)
    List<Object[]> findVendasPorCidade();

//    // Buscar todos os bundles pelo id do usuário
//    @Query("SELECT b FROM Bundle b WHERE b.user.id = :userId")
//    List<Bundle> getAllBundlesByUser(@Param("userId") Integer userId);
}
