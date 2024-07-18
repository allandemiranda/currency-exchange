package app.bank.dummy.dtos;

import app.bank.dummy.enums.TransactionType;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link app.bank.dummy.entities.Transaction}
 */
public record TransactionDto(UUID id, LocalDateTime dataTime, double amount, double taxRate, double tmpBalance, TransactionType type, UUID account) implements Serializable {

  @Serial
  private static final long serialVersionUID = -6652091499004403908L;
}