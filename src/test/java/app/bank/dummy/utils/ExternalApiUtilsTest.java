package app.bank.dummy.utils;

import app.bank.dummy.enums.Currency;
import app.bank.dummy.exceptions.RateTaxUnavailableException;
import java.net.http.HttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExternalApiUtilsTest {

  @Test
  void testGetTaxRateShouldThrowRateTaxUnavailableException() {
    try (MockedStatic<HttpClient> httpClientMockedStatic = Mockito.mockStatic(HttpClient.class)) {
      //given
      final String key = "key";
      final Currency debitCurrency = Mockito.mock(Currency.class);
      final Currency creditCurrency = Mockito.mock(Currency.class);
      //when
      Mockito.when(creditCurrency.getName()).thenReturn("EUR");
      httpClientMockedStatic.when(HttpClient::newHttpClient).thenThrow(new RateTaxUnavailableException());
      final Executable executable = () -> ExternalApiUtils.getTaxRate(debitCurrency, creditCurrency, key);
      //then
      Assertions.assertThrows(RateTaxUnavailableException.class, executable);
    }

  }
}