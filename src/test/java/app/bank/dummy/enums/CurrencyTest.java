package app.bank.dummy.enums;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class CurrencyTest {

  @ParameterizedTest
  @EnumSource(Currency.class)
  void testGetNameNotNull(final @NonNull Currency currency) {
    //given
    final String name = currency.getName();
    //when
    //then
    Assertions.assertNotNull(name);
  }

  @ParameterizedTest
  @EnumSource(Currency.class)
  void testGetNameNotBlank(final @NonNull Currency currency) {
    //given
    final String name = currency.getName();
    //when
    //then
    Assertions.assertFalse(name.isBlank());
  }

  @ParameterizedTest
  @EnumSource(Currency.class)
  void testGetNameSize(final @NonNull Currency currency) {
    //given
    final String name = currency.getName();
    //when
    final int size = name.length();
    //then
    Assertions.assertEquals(3, size);
  }

  @ParameterizedTest
  @EnumSource(Currency.class)
  void testGetDescriptionNotNull(final @NonNull Currency currency) {
    //given
    final String name = currency.getDescription();
    //when
    //then
    Assertions.assertNotNull(name);
  }

  @ParameterizedTest
  @EnumSource(Currency.class)
  void testGetDescriptionNotBlank(final @NonNull Currency currency) {
    //given
    final String name = currency.getDescription();
    //when
    //then
    Assertions.assertFalse(name.isBlank());
  }

  @ParameterizedTest
  @EnumSource(Currency.class)
  void testGetDescriptionNotEmpty(final @NonNull Currency currency) {
    //given
    final String name = currency.getDescription();
    //when
    //then
    Assertions.assertFalse(name.isEmpty());
  }
}