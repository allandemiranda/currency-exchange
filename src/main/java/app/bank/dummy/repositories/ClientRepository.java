package app.bank.dummy.repositories;

import app.bank.dummy.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}