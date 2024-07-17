package app.bank.dummy.providers;

import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.NewCurrencyDto;
import app.bank.dummy.exceptions.CurrencyNotFoundException;
import app.bank.dummy.mappers.CurrencyMapper;
import app.bank.dummy.models.Currency;
import app.bank.dummy.repositories.CurrencyRepository;
import app.bank.dummy.services.CurrencyService;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class CurrencyProvider implements CurrencyService {

  private final CurrencyRepository currencyRepository;
  private final CurrencyMapper currencyMapper;

//  @Override
//  public @NonNull CurrencyDto getCurrencyByCode(final @NonNull String currencyCode, final @NonNull String rateCurrencyCode) {
//    final Currency currency = this.getCurrencyRepository().findById(currencyCode).orElseThrow(CurrencyNotFoundException::new);
//    final Currency currencyRate = this.getCurrencyRepository().findById(rateCurrencyCode).orElseThrow(CurrencyNotFoundException::new);
//
//    // Setting URL
//    String url_str = "https://v6.exchangerate-api.com/v6/9971c4dddd4d3aaccdc6fb2b/latest/" + currency.getCode();
//
//    try {
//      // Making Request
//      URL url = new URL(url_str);
//      HttpURLConnection request = (HttpURLConnection) url.openConnection();
//      request.connect();
//
//      // Convert to JSON
//      JsonParser jp = new JsonParser();
//      JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
//      JsonObject jsonobj = root.getAsJsonObject();
//
//      // Accessing object
//      String req_result = jsonobj.get("result").getAsString();
//      System.out.println("result: " + req_result);
//
//      System.out.println("Rate: " + jsonobj.asMap().get("conversion_rates").getAsJsonObject().asMap().get(currencyRate.getCode()).getAsDouble());
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//
//    return this.getCurrencyMapper().toDto(currency, 1.2);
//  }

  @Override
  public double getTaxRate(final @NonNull CurrencyDto debitCurrency, final @NonNull CurrencyDto creditCurrency) {
    final String debitCode = debitCurrency.code();
    final String creditCode = creditCurrency.code();
    return 1.2;
  }

  @Override
  public CurrencyDto getCurrencyByCode(final @NonNull String code) {
    final Currency currency = this.getCurrencyRepository().findByCode(code).orElseThrow(() -> new CurrencyNotFoundException(code));
    return this.getCurrencyMapper().toDto(currency);
  }

  @Override
  public Collection<@NotNull CurrencyDto> getAllCurrencies() {
    final Collection<Currency> currencies = this.getCurrencyRepository().findAll();
    return currencies.stream().map(this.getCurrencyMapper()::toDto).toList();
  }

  @Override
  public CurrencyDto createCurrency(final @NotNull NewCurrencyDto newCurrencyDto) {
    final Currency currencyEntity = this.getCurrencyMapper().toEntity(newCurrencyDto);
    final Currency savedCurrency = this.getCurrencyRepository().save(currencyEntity);
    return this.getCurrencyMapper().toDto(savedCurrency);
  }
}
