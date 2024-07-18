package app.bank.dummy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
@Embeddable
public class TransactionInfo implements Serializable {

  @Serial
  private static final long serialVersionUID = -7863893130626747168L;

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  @Column(name = "tmp_balance", nullable = false, updatable = false)
  private double tmpBalance;

}