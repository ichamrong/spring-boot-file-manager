package com.ichamrong.iprofileservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

  /* ---------- Identity ---------- */
  @Id private String id; // Mirror of Keycloak user-ID (UUID)

  @Column(nullable = false, updatable = false)
  private String username;

  @Column(nullable = false)
  private String email;

  private String firstName;
  private String lastName;

  private boolean enabled;
  private boolean emailVerified;

  /* ---------- Profile extras ---------- */
  private String avatarUrl;
  private String phone;
  private String address;
  private String locale; // “en_US”, “km_KH”, …

  /* ---------- Relationships ---------- */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "position_id")
  @Exclude
  private Position position;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id")
  @Exclude
  private Tenant tenant;

  /* ---------- Auditing ---------- */
  @Builder.Default private Instant createdAt = Instant.now();

  private Instant updatedAt;

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    User user = (User) o;
    return getId() != null && Objects.equals(getId(), user.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
        : getClass().hashCode();
  }
}
