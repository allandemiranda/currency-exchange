package app.bank.dummy.entities;

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
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "transaction")
public class Transaction implements Serializable {

  @Serial
  private static final long serialVersionUID = 1016254965437964086L;

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @JdbcTypeCode(SqlTypes.UUID)
  private UUID id;

  @NotNull
  @PastOrPresent
  @Column(name = "data_time", nullable = false, updatable = false)
  @JdbcTypeCode(SqlTypes.TIMESTAMP)
  private LocalDateTime dataTime;

  @Positive
  @Column(name = "amount", nullable = false, updatable = false)
  private double amount;

  @Positive
  @Column(name = "tax_rate", nullable = false, updatable = false)
  private double taxRate;

  @NotNull
  @ManyToOne(optional = false, cascade = CascadeType.MERGE)
  @JoinColumn(name = "debit_account_id", nullable = false, updatable = false)
  @JdbcTypeCode(SqlTypes.UUID)
  private Account debitAccount;

  @PositiveOrZero
  @Column(name = "debit_account_tmp_balance", nullable = false, updatable = false)
  private double debitTmpBalance;

  @NotNull
  @ManyToOne(optional = false, cascade = CascadeType.MERGE)
  @JoinColumn(name = "credit_account_id", nullable = false, updatable = false)
  @JdbcTypeCode(SqlTypes.UUID)
  private Account creditAccount;

  @PositiveOrZero
  @Column(name = "credit_account_tmp_balance", nullable = false, updatable = false)
  private double creditTmpBalance;

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
    final Transaction that = (Transaction) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }
}
