package app.bank.dummy.dtos;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionDto(UUID id, LocalDateTime dataTime, double amount, double taxRate, UUID debitAccount, UUID creditAccount) implements Serializable {

  @Serial
  private static final long serialVersionUID = 5465162524688743124L;

}
