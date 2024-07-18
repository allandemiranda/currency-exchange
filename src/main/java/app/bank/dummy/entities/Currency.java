package app.bank.dummy.entities;

import app.bank.dummy.listeners.CurrencyListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
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
@EntityListeners(CurrencyListener.class)
@Table(name = "currency")
public class Currency implements Serializable {

  @Serial
  private static final long serialVersionUID = 4772984443652657322L;

  @Id
  @NotNull
  @Size(min = 3, max = 3, message = "{currency.code.validation.message}")
  @Column(name = "code", nullable = false, length = 3, unique = true)
  @JdbcTypeCode(SqlTypes.CHAR)
  private String code;

  @NotNull
  @NotBlank
  @NotEmpty
  @Column(name = "name", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String name;

  @NotNull
  @NotBlank
  @NotEmpty
  @Column(name = "symbol", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String symbol;

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
    final Currency currency = (Currency) o;
    return getCode() != null && Objects.equals(getCode(), currency.getCode());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}
