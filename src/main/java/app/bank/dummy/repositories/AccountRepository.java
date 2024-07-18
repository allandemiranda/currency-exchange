package app.bank.dummy.repositories;

import app.bank.dummy.entities.Account;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {

}