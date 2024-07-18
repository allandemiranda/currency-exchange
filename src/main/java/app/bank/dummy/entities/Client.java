package app.bank.dummy.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
import lombok.ToString.Exclude;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Serializable {

  @Serial
  private static final long serialVersionUID = 1016254965437964086L;

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @JdbcTypeCode(SqlTypes.NUMERIC)
  private Long id;

  @NotNull
  @NotEmpty
  @NotBlank
  @Column(name = "name", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String name;

  @NotNull
  @NotEmpty
  @NotBlank
  @Column(name = "login", nullable = false, unique = true)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String login;

  @Exclude
  @NotNull
  @Size(min = 5)
  @Column(name = "password", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String password;

  @NotNull
  @OneToOne(cascade = CascadeType.PERSIST, optional = false, orphanRemoval = true)
  @JoinColumn(name = "account_id", nullable = false, unique = true)
  @JdbcTypeCode(SqlTypes.UUID)
  private Account account;

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
    final Client client = (Client) o;
    return getId() != null && Objects.equals(getId(), client.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}
