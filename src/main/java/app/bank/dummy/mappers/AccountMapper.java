package app.bank.dummy.mappers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientAccountDto;
import app.bank.dummy.dtos.NewAccountDto;
import app.bank.dummy.entities.Account;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface AccountMapper {

  @NotNull
  @Mapping(target = "id", source = "account.id")
  @Mapping(target = "balance", source = "account.balance")
  @Mapping(target = "status", source = "account.status")
  @Mapping(target = "currency", source = "account.currency")
  ClientAccountDto toDtoClient(final @NotNull Account account);

  @NotNull
  @Mapping(target = "currency", source = "currency")
  @Mapping(target = "balance", source = "initialBalance")
  Account toEntity(final @NotNull NewAccountDto newAccountDto);

  @NotNull
  @Mapping(target = "id", source = "account.id")
  @Mapping(target = "status", source = "account.status")
  @Mapping(target = "currency", source = "account.currency")
  AccountDto toDto(final @NotNull Account account);
}