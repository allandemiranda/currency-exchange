package app.bank.dummy.enums;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountStatusTest {

  @Test
  void testAccountStatusSize() {
    //given
    final int target = 2;
    //when
    final int realSize = AccountStatus.values().length;
    //then
    Assertions.assertEquals(target, realSize);
  }

  @Test
  void testValuesForAccountStatus() {
    //given
    final AccountStatus open = AccountStatus.valueOf("OPEN");
    final AccountStatus close = AccountStatus.valueOf("CLOSE");
    //when
    final AccountStatus[] accountStatus = AccountStatus.values();
    //then
    Assertions.assertTrue(Arrays.asList(accountStatus).contains(open));
    Assertions.assertTrue(Arrays.asList(accountStatus).contains(close));
  }

}