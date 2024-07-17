package app.bank.dummy.projections;

import app.bank.dummy.models.Currency;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "currency", types = {Currency.class})
public interface CurrencyProjection {

  String getCode();

  String getName();

  String getSymbol();
}
