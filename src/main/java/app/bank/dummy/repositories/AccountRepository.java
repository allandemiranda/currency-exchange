package app.bank.dummy.repositories;

import app.bank.dummy.entities.Account;
import app.bank.dummy.enums.AccountStatus;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<Account, UUID> {

  @Transactional(readOnly = true)
  Collection<Account> findByClient_Id(@NonNull Long id);

  @Transactional(readOnly = true)
  Optional<Account> findByIdAndStatus(@NonNull UUID id, @NonNull AccountStatus status);
}