package com.decolatech.easytravel.domain.bundle.entity;

import com.decolatech.easytravel.domain.bundle.enums.BundleRank;
import com.decolatech.easytravel.domain.bundle.enums.BundleStatus;
import com.decolatech.easytravel.domain.booking.entity.Reservation;
import com.decolatech.easytravel.domain.logs.entity.LogBundle;
import com.decolatech.easytravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_BUNDLES")
@Getter
@Setter
@ToString(exclude = {"bundleLocations", "medias", "reservations", "logEntries"})
@NoArgsConstructor
@AllArgsConstructor
public class Bundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "BUNDLE_TITLE", nullable = false, length = 100)
    private String bundleTitle;

    @Column(name = "BUNDLE_DESCRIPTION")
    private String bundleDescription;

    @Column(name = "INITIAL_PRICE", nullable = false)
    private Double initialPrice;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "BUNDLE_RANK", nullable = false)
    private BundleRank bundleRank;

    @Column(name = "INITIAL_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime initialDate;

    @Column(name = "FINAL_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime finalDate;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "TRAVELERS_NUMBER", nullable = false)
    private Integer travelersNumber;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "BUNDLE_STATUS", nullable = false)
    private BundleStatus bundleStatus = BundleStatus.AVAILABLE;

    // Relacionamentos
    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BundleLocation> bundleLocations;

    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Media> medias;

    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

//    @OneToMany(mappedBy = "bundle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<LogBundle> logEntries;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

}
