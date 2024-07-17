package app.bank.dummy.providers;

import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.repositories.CurrencyRepository;
import app.bank.dummy.services.CurrencyService;
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
  public double getCreditAmount(final @NonNull CurrencyDto debitCurrency, final @NonNull CurrencyDto creditCurrency, final double amount) {
    final String debitCode = debitCurrency.code();
    final String creditCode = creditCurrency.code();
    return amount * 1.2;
  }
}
