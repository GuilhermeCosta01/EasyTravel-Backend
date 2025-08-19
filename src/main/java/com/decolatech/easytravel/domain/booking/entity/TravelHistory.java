package com.decolatech.easytravel.domain.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TB_TRAVEL_HISTORY")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TravelHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    // Removido relacionamento com Review, pois agora a FK está em Review

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_TB_PAYMENT", nullable = false)
    private Payment payment;

    @Column(name = "PDF", length = 210)
    private String pdf;
}
