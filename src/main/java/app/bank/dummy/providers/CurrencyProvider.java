package app.bank.dummy.providers;

import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.NewCurrencyDto;
import app.bank.dummy.dtos.UpdateCurrencyDto;
import app.bank.dummy.entities.Currency;
import app.bank.dummy.exceptions.AccountNotFoundException;
import app.bank.dummy.exceptions.CurrencyNotFoundException;
import app.bank.dummy.mappers.CurrencyMapper;
import app.bank.dummy.repositories.AccountRepository;
import app.bank.dummy.repositories.CurrencyRepository;
import app.bank.dummy.services.CurrencyService;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class CurrencyProvider implements CurrencyService {

  private final CurrencyRepository currencyRepository;
  private final AccountRepository accountRepository;
  private final CurrencyMapper currencyMapper;
  @Value("${external-api.key}")
  private String key;

  @Override
  public double getTaxRate(final UUID debitAccountId, final UUID creditAccountId) {
    final String debitCode = this.getAccountRepository().findById(debitAccountId).orElseThrow(() -> new AccountNotFoundException(debitAccountId)).getCurrency().getCode();
    final String creditCode = this.getAccountRepository().findById(creditAccountId).orElseThrow(() -> new AccountNotFoundException(creditAccountId)).getCurrency().getCode();
//
//    final String url = "https://v6.exchangerate-api.com/v6/".concat(key).concat("/latest/").concat(creditCode);
//    final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).method("GET", HttpRequest.BodyPublishers.noBody()).build();
//
//    try (final HttpClient httpClient = HttpClient.newHttpClient()) {
//      final HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
//      final JsonElement root = JsonParser.parseReader(new InputStreamReader(response.body()));
//      return root.getAsJsonObject().asMap().get("conversion_rates").getAsJsonObject().asMap().get(debitCode).getAsDouble();
//    } catch (IOException | InterruptedException e) {
//      Thread.currentThread().interrupt();
//      throw new RateTaxUnavailableException();
//    }
//    throw new RateTaxUnavailableException("ERRO TESTE");
    return 1.5;
  }

  @Override
  public CurrencyDto getCurrencyByCode(final @NonNull String code) {
    final Currency currency = this.getCurrencyRepository().findById(code).orElseThrow(() -> new CurrencyNotFoundException(code));
    return this.getCurrencyMapper().toDto(currency);
  }

  @Override
  public Collection<@NotNull CurrencyDto> getAllCurrencies() {
    final Collection<Currency> currencies = this.getCurrencyRepository().findAll();
    return currencies.stream().map(this.getCurrencyMapper()::toDto).toList();
  }

  @Override
  public CurrencyDto createCurrency(final @NotNull NewCurrencyDto newCurrencyDto) {
    final Currency newCurrency = this.getCurrencyMapper().toEntity(newCurrencyDto);
    final Currency savedCurrency = this.getCurrencyRepository().save(newCurrency);
    return this.getCurrencyMapper().toDto(savedCurrency);
  }

  @Override
  public @NotNull CurrencyDto updateCurrency(final String code, final @NonNull UpdateCurrencyDto updateCurrencyDto) {
    final Currency currency = this.getCurrencyRepository().findById(code).orElseThrow(() -> new CurrencyNotFoundException(code));
    final Currency currencyToUpdate = this.getCurrencyMapper().partialUpdate(updateCurrencyDto, currency);
    final Currency savedCurrency = this.getCurrencyRepository().save(currencyToUpdate);
    return this.getCurrencyMapper().toDto(savedCurrency);
  }
}
