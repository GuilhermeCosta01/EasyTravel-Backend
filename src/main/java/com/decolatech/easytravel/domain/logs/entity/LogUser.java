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
@Table(name = "TB_LOG_USERS")
@Getter
@Setter
@ToString(exclude = {"userTarget", "userAction"})
@NoArgsConstructor
@AllArgsConstructor
public class LogUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOG_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime logDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_TARGET", nullable = false)
    private User userTarget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_ACTION")
    private User userAction;

    @Column(name = "FIELD_CHANGED", nullable = false, length = 50)
    private String fieldChanged;

    @Column(name = "OLD_VALUE", length = 255)
    private String oldValue;

    @Column(name = "NEW_VALUE", length = 255)
    private String newValue;
}
