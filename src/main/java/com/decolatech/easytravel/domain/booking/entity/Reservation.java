package com.decolatech.easytravel.domain.booking.entity;

import com.decolatech.easytravel.domain.booking.enums.ReservationStatus;
import com.decolatech.easytravel.domain.bundle.entity.Bundle;
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
@Table(name = "TB_RESERVATIONS")
@Getter
@Setter
@ToString(exclude = {"user", "bundle", "travelers", "payments", "logEntries"})
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "RESERV_STATUS", nullable = false)
    private ReservationStatus reservStatus = ReservationStatus.PENDING;

    @Column(name = "RESERV_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime reservDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BUNDLE", nullable = false)
    private Bundle bundle;

    // Relacionamentos
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Traveler> travelers;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;


}
