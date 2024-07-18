package app.bank.dummy.repositories;

import app.bank.dummy.entities.Account;
import app.bank.dummy.enums.AccountStatus;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface AccountRepository extends JpaRepository<Account, UUID> {

  Collection<Account> findByClient_Id(@NonNull Long id);

  Optional<Account> findByIdAndStatus(@NonNull UUID id, @NonNull AccountStatus status);
}