package app.bank.dummy.listeners;

import app.bank.dummy.entities.Transaction;
import jakarta.persistence.PrePersist;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

  @PrePersist
  public void prePersist(final @NonNull Transaction transaction) {
    final double debitBalance = transaction.getDebitInfo().getAccount().getBalance();
    transaction.getDebitInfo().setTmpBalance(debitBalance);
    final double creditBalance = transaction.getCreditInfo().getAccount().getBalance();
    transaction.getCreditInfo().setTmpBalance(creditBalance);
  }
}
