package app.bank.dummy.listeners;

import app.bank.dummy.entities.Currency;
import jakarta.persistence.PrePersist;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CurrencyListener {

  @PrePersist
  public void prePersist(final @NonNull Currency currency) {
    currency.setCode(currency.getCode().toUpperCase());
  }

}
