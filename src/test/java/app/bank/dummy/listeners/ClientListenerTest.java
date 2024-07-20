package app.bank.dummy.listeners;

import app.bank.dummy.entities.Client;
import app.bank.dummy.entities.ClientCredentials;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.DigestUtils;

class ClientListenerTest {

  @Test
  void testShouldEncryptPassword() {
    //given
    final ClientListener clientListener = new ClientListener();
    final Client client = Mockito.mock(Client.class);
    final ClientCredentials credentials = Mockito.mock(ClientCredentials.class);
    final String plainPassword = "password";
    final String encryptedPassword = DigestUtils.md5DigestAsHex(plainPassword.getBytes());
    //when
    Mockito.when(credentials.getPassword()).thenReturn(plainPassword);
    Mockito.when(client.getCredentials()).thenReturn(credentials);
    clientListener.prePersist(client);
    //then
    Mockito.verify(credentials).setPassword(encryptedPassword);
    Mockito.verify(client.getCredentials(), Mockito.times(1)).getPassword();
    Mockito.verify(client.getCredentials(), Mockito.times(1)).setPassword(encryptedPassword);
  }

  @Test
  void testNullClientShouldThrowException() {
    //given
    final ClientListener clientListener = new ClientListener();
    //when
    Assertions.assertThrows(NullPointerException.class, () -> clientListener.prePersist(null));
  }
}