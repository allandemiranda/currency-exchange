package app.bank.dummy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString.Exclude;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Embeddable
public class ClientCredentials implements Serializable {

  @Serial
  private static final long serialVersionUID = -1107141167455417339L;

  @NotNull
  @NotEmpty
  @NotBlank
  @Column(name = "login", nullable = false, unique = true)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String login;

  @Exclude
  @NotNull
  @Size(min = 5, message = "{client.credentials.password.validation.message}")
  @Column(name = "password", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String password;

}