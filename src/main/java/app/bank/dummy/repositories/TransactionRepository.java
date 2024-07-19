package app.bank.dummy.repositories;

import app.bank.dummy.entities.Transaction;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  @NonNull
  @Transactional(readOnly = true)
  Collection<Transaction> findByDebitInfo_Account_Id(final @NonNull UUID id);

  @NonNull
  @Transactional(readOnly = true)
  Collection<Transaction> findByCreditInfo_Account_Id(final @NonNull UUID id);
}
