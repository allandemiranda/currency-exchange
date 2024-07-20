package app.bank.dummy.listeners;

import app.bank.dummy.entities.Client;
import app.bank.dummy.entities.ClientCredentials;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.util.DigestUtils;

class ClientListenerTest {

  @Test
  void testShouldEncryptPassword() {
    //given
    final ClientListener clientListener = new ClientListener();
    final Client client = Mockito.spy(Client.class);
    final ClientCredentials credentials = Mockito.spy(ClientCredentials.class);
    final String plainPassword = "password";
    final String encryptedPassword = DigestUtils.md5DigestAsHex(plainPassword.getBytes());
    //when
    Mockito.when(client.getCredentials()).thenReturn(credentials);
    client.getCredentials().setPassword(plainPassword);
    clientListener.prePersist(client);
    //then
    Mockito.verify(credentials).setPassword(encryptedPassword);
    Mockito.verify(client.getCredentials()).getPassword();
    Mockito.verify(client.getCredentials()).setPassword(encryptedPassword);
    Assertions.assertEquals(encryptedPassword, client.getCredentials().getPassword());
  }

  @Test
  void testNullClientShouldThrowException() {
    //given
    final ClientListener clientListener = new ClientListener();
    //when
    final Executable executable = () -> clientListener.prePersist(null);
    //then
    Assertions.assertThrows(NullPointerException.class, executable);
  }
}