package app.bank.dummy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Embeddable
public class ClientInfo implements Serializable {

  @Serial
  private static final long serialVersionUID = 2946203803060121080L;

  @NotNull
  @NotEmpty
  @NotBlank
  @Column(name = "first_name", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String firstName;

  @NotNull
  @NotEmpty
  @NotBlank
  @Column(name = "last_name", nullable = false)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private String lastName;

}