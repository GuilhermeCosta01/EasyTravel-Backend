package com.decolatech.easytravel.domain.bundle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "TB_LOCATIONS")
@Getter
@Setter
@ToString(exclude = {"departureLocations", "destinationLocations"})
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "COUNTRY", nullable = false, length = 5)
    private String country;

    @Column(name = "STATES", nullable = false, length = 2)
    private String states;

    @Column(name = "CITY", nullable = false, length = 50)
    private String city;

    // Relacionamentos
    @OneToMany(mappedBy = "departure", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BundleLocation> departureLocations;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BundleLocation> destinationLocations;
}
