package app.bank.dummy.repositories;

import app.bank.dummy.entities.Account;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<Account, UUID> {

  @NonNull
  @Transactional(readOnly = true)
  Collection<Account> findByClient_Id(final @NonNull Long id);
}