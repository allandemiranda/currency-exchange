package app.bank.dummy.repositories;

import app.bank.dummy.models.Currency;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {

  @Transactional(readOnly = true)
  Optional<Currency> findByCode(@NonNull String code);
}