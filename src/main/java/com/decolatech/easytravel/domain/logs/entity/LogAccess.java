package com.decolatech.easytravel.domain.logs.entity;

import com.decolatech.easytravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_LOG_ACCESS")
@Getter
@Setter
@ToString(exclude = {"user"})
@NoArgsConstructor
@AllArgsConstructor
public class LogAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOG_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime logDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER")
    private User user;

    @Column(name = "ACTION_TYPE", nullable = false, length = 50)
    private String actionType;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;
}
