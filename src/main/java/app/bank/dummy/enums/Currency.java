package app.bank.dummy.enums;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
  //@formatter:off
  EUR("EUR", "Euro"),
  USD("USD", "US Dollar"),
  GBP("GBP", "Pound Sterling"),
  CHF("CHF", "Swiss Franc"),
  JPY("JPY", "Yen"),
  NZD("NZD", "New Zealand dollar"),
  AUD("AUD", "Australian dollar"),
  CAD("CAD", "Canadian dollar"),
  CNH("CNH", "Chinese Yuan Offshore"),
  SEK("SEK", "Swedish krona");
  //@formatter:on

  @NonNull
  private final String name;
  @NonNull
  private final String description;
}
