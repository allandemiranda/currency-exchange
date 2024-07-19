package app.bank.dummy.repositories;

import app.bank.dummy.entities.Transaction;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  Collection<Transaction> findByDebitInfo_Account_Id(@NonNull UUID id);

  Collection<Transaction> findByCreditInfo_Account_Id(@NonNull UUID id);
}
