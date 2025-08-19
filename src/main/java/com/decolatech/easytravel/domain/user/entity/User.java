package com.decolatech.easytravel.domain.user.entity;

import com.decolatech.easytravel.domain.user.enums.UserRole;
import com.decolatech.easytravel.domain.user.enums.UserStatus;
import com.decolatech.easytravel.domain.booking.entity.Reservation;
import com.decolatech.easytravel.domain.logs.entity.LogUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "TB_USERS")
@Getter
@Setter
@ToString(exclude = {"password", "reservations"})
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USERNAME", nullable = false, length = 100)
    private String name;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @Column(name = "CPF", nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(name = "PASSPORT", length = 10, unique = false, nullable = true)
    private String passport;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @Column(name = "TELEPHONE", nullable = false, length = 18)
    private String telephone;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "USER_STATUS", nullable = false)
    private UserStatus userStatus = UserStatus.ACTIVATED;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "USER_ROLE", nullable = false)
    private UserRole userRole;

    // Relacionamentos
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    // Relacionamentos com logs
    @OneToMany(mappedBy = "userTarget", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LogUser> logEntriesAsTarget;

    @OneToMany(mappedBy = "userAction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LogUser> logEntriesAsAction;

    public User (String name,String email, String cpf,String passport, String password, String telephone, UserRole role) {
        this.setName(name);
        this.setEmail(email);
        this.setCpf(cpf);
        this.setPassport(passport);
        this.setPassword(password);
        this.setTelephone(telephone);
        this.setUserRole(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.getUserRole() == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_EMPLOYEE"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        } else if (this.getUserRole() == UserRole.EMPLOYEE) {
            return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }




    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userStatus != UserStatus.DEACTIVATED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return userStatus != UserStatus.DEACTIVATED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userStatus != UserStatus.DEACTIVATED;
    }

    @Override
    public boolean isEnabled() {
        return userStatus != UserStatus.DEACTIVATED;
    }
}
