package app.bank.dummy.repositories;

import app.bank.dummy.models.Account;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AccountRepository extends CrudRepository<Account, UUID> {

}