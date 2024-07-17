package app.bank.dummy.repositories;

import app.bank.dummy.models.Transaction;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {

}
