package com.decolatech.easytravel.domain.booking.entity;

import com.decolatech.easytravel.domain.booking.enums.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_REVIEWS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "RATING", nullable = false)
    private Rating rating;

    @Column(name = "COMMENT", length = 100)
    private String comment;

    @Column(name = "AVALIATION_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime avaliationDate;

    @OneToOne
    @JoinColumn(name = "FK_TB_TRAVEL_HISTORY", nullable = false)
    private TravelHistory travelHistory;

    @ManyToOne
    @JoinColumn(name = "ID_BUNDLE", nullable = false)
    private com.decolatech.easytravel.domain.bundle.entity.Bundle bundle;
}
