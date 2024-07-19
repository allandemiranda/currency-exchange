package app.bank.dummy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
@Embeddable
public class TransactionDebitInfo implements Serializable {

  @Serial
  private static final long serialVersionUID = -7863893130626747168L;

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "debit_account_id", nullable = false)
  private Account account;

  @PositiveOrZero
  @Column(name = "debit_tmp_balance", nullable = false)
  private double tmpBalance;

}