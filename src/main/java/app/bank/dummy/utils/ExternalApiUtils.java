package app.bank.dummy.utils;

import app.bank.dummy.enums.Currency;
import app.bank.dummy.exceptions.RateTaxUnavailableException;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExternalApiUtils {

  private static final String API_URL = "https://v6.exchangerate-api.com/v6/";
  private static final String TIME = "/latest/";
  private static final String METHOD = "GET";
  private static final String JSON_KEY = "conversion_rates";

  @Positive
  public double getTaxRate(final @NonNull Currency debitCurrency, final @NonNull Currency creditCurrency, final @NonNull String key) {
    final String url = API_URL.concat(key).concat(TIME).concat(creditCurrency.getName());
    final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).method(METHOD, HttpRequest.BodyPublishers.noBody()).build();

    try (final HttpClient httpClient = HttpClient.newHttpClient()) {
      final HttpResponse<InputStream> response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
      final JsonElement root = JsonParser.parseReader(new InputStreamReader(response.body()));
      return root.getAsJsonObject().asMap().get(JSON_KEY).getAsJsonObject().asMap().get(debitCurrency.getName()).getAsDouble();
    } catch (IOException | InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RateTaxUnavailableException();
    }
  }

}
