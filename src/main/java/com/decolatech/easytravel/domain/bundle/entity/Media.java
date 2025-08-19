package com.decolatech.easytravel.domain.bundle.entity;

import com.decolatech.easytravel.domain.bundle.enums.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TB_MEDIAS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "MEDIA_TYPE", nullable = false)
    private MediaType mediaType;

    @Column(name = "MEDIA_URL", nullable = false)
    private String mediaUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_BUNDLE", nullable = false)
    private Bundle bundle;
}
