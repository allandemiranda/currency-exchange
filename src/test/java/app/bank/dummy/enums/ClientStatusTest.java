package app.bank.dummy.enums;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClientStatusTest {

  @Test
  void testClientStatusSize() {
    //given
    final int target = 2;
    //when
    final int realSize = ClientStatus.values().length;
    //then
    Assertions.assertEquals(target, realSize);
  }

  @Test
  void testValuesForClientStatus() {
    //given
    final ClientStatus activate = ClientStatus.valueOf("ACTIVATE");
    final ClientStatus deactivate = ClientStatus.valueOf("DEACTIVATE");
    //when
    final ClientStatus[] clientStatus = ClientStatus.values();
    //then
    Assertions.assertTrue(Arrays.asList(clientStatus).contains(activate));
    Assertions.assertTrue(Arrays.asList(clientStatus).contains(deactivate));
  }
}