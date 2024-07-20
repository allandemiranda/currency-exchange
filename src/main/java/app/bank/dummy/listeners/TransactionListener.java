package app.bank.dummy.listeners;

import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Transaction;
import app.bank.dummy.entities.TransactionCreditInfo;
import app.bank.dummy.entities.TransactionDebitInfo;
import jakarta.persistence.PrePersist;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

  @PrePersist
  public void prePersist(final @NonNull Transaction transaction) {
    final TransactionDebitInfo debitInfo = transaction.getDebitInfo();
    final Account debitAccount = debitInfo.getAccount();
    debitInfo.setTmpBalance(debitAccount.getBalance());

    final TransactionCreditInfo creditInfo = transaction.getCreditInfo();
    final Account creditAccount = creditInfo.getAccount();
    creditInfo.setTmpBalance(creditAccount.getBalance());
  }
}
