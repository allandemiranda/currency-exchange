package app.bank.dummy.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "account")
public class Account implements Serializable {

  @Serial
  private static final long serialVersionUID = -2969862957237720531L;

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, unique = true, updatable = false)
  @JdbcTypeCode(SqlTypes.UUID)
  private UUID id;

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "currency_id", nullable = false, updatable = false)
  @JdbcTypeCode(SqlTypes.UUID)
  private Currency currency;

  @PositiveOrZero
  @Column(name = "balance", nullable = false)
  @JdbcTypeCode(SqlTypes.DOUBLE)
  private double balance;

  @Override
  public final boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    final Account account = (Account) o;
    return getId() != null && Objects.equals(getId(), account.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}