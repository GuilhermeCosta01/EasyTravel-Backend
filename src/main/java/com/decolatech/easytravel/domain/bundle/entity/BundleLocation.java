package com.decolatech.easytravel.domain.bundle.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TB_BUNDLE_LOCATIONS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BundleLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Schema(description = "ID do bundle")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BUNDLE", nullable = false)
    private Bundle bundle;

    @Schema(description = "ID do destino", type = "integer", example = "2")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DESTINATION", nullable = false)
    private Location destination;

    @Schema(description = "ID da origem", type = "integer", example = "1")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DEPARTURE", nullable = false)
    private Location departure;
}
