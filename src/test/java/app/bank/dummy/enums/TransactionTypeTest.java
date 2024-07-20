package app.bank.dummy.enums;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransactionTypeTest {

  @Test
  void testTransactionTypeSize() {
    //given
    final int target = 2;
    //when
    final int realSize = TransactionType.values().length;
    //then
    Assertions.assertEquals(target, realSize);
  }

  @Test
  void testValuesForAccountStatus() {
    //given
    final TransactionType debit = TransactionType.valueOf("DEBIT");
    final TransactionType credit = TransactionType.valueOf("CREDIT");
    //when
    final TransactionType[] transactionType = TransactionType.values();
    //then
    Assertions.assertTrue(Arrays.asList(transactionType).contains(debit));
    Assertions.assertTrue(Arrays.asList(transactionType).contains(credit));
  }
}