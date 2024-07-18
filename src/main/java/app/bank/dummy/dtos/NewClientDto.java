package app.bank.dummy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

/**
 * DTO for {@link app.bank.dummy.entities.Client}
 */
public record NewClientDto(@NotNull @NotBlank @NotEmpty String firstName, @NotNull @NotBlank @NotEmpty String lastName, @NotNull @NotBlank @NotEmpty String login,
                           @NotNull @Size(min = 5, message = "{client.credentials.password.validation.message}") String password) implements Serializable {

  @Serial
  private static final long serialVersionUID = 4529695744952952529L;
}