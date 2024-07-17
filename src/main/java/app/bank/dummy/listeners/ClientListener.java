package app.bank.dummy.listeners;

import app.bank.dummy.models.Client;
import jakarta.persistence.PrePersist;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class ClientListener {

  @PrePersist
  public void prePersist(final @NonNull Client client) {
    final String passwordEncrypted = DigestUtils.md5DigestAsHex(client.getPassword().getBytes());
    client.setPassword(passwordEncrypted);
  }
}
