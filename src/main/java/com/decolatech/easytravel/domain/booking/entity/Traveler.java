package com.decolatech.easytravel.domain.booking.entity;

import com.decolatech.easytravel.domain.booking.enums.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TB_TRAVELERS")
@Getter
@Setter
@ToString(exclude = {"reservation"})
@NoArgsConstructor
@AllArgsConstructor
public class Traveler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FULL_NAME", nullable = false, length = 100)
    private String fullName;

    @Column(name = "DOCUMENT_NUMBER", nullable = false, length = 20)
    private String documentNumber;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "DOCUMENT_TYPE", nullable = false)
    private DocumentType documentType;

    @Column(name = "AGE", nullable = false)
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESERVATION", nullable = false)
    private Reservation reservation;
}
