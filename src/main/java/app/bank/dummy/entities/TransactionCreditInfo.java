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
public class TransactionCreditInfo implements Serializable {

  @Serial
  private static final long serialVersionUID = 4642532559642829109L;

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "credit_account_id", nullable = false)
  private Account account;

  @PositiveOrZero
  @Column(name = "credit_tmp_balance", nullable = false)
  private double tmpBalance;

}