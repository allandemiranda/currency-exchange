package app.bank.dummy.listeners;

import app.bank.dummy.entities.Client;
import jakarta.persistence.PrePersist;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class ClientListener {

  @PrePersist
  public void prePersist(final @NonNull Client client) {
    final String passwordEncrypted = DigestUtils.md5DigestAsHex(client.getCredentials().getPassword().getBytes());
    client.getCredentials().setPassword(passwordEncrypted);
  }
}
