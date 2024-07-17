package app.bank.dummy.repositories;

import app.bank.dummy.models.Currency;
import app.bank.dummy.projections.CurrencyProjection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.lang.NonNull;

@RepositoryRestResource(excerptProjection = CurrencyProjection.class)
public interface CurrencyRepository extends CrudRepository<Currency, UUID>, PagingAndSortingRepository<Currency, UUID> {

  @Override
  @RestResource(exported = false)
  void deleteById(@NonNull UUID id);

  @RestResource()
  Optional<Currency> findByCode(@NonNull String code);

}