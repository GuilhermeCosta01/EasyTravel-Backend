package com.decolatech.easytravel.domain.booking.entity;

import com.decolatech.easytravel.domain.booking.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_PAYMENTS")
@Getter
@Setter
@ToString(exclude = {"reservation", "logEntries", "travelHistories"})
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PAYMENT_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "PAYMENT_METHOD", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "TOT_PRICE", nullable = false)
    private Double totPrice;

    @Column(name = "INSTALLMENTS")
    private Integer installments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESERVATION", nullable = false)
    private Reservation reservation;

    // Relacionamentos


    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TravelHistory> travelHistories;
}
