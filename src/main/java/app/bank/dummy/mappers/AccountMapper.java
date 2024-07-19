package app.bank.dummy.mappers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientAccountDto;
import app.bank.dummy.dtos.NewAccountDto;
import app.bank.dummy.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface AccountMapper {

  @Mapping(target = "id", source = "account.id")
  @Mapping(target = "balance", source = "account.balance")
  @Mapping(target = "status", source = "account.status")
  ClientAccountDto toDtoClient(Account account);

  @Mapping(target = "currency", source = "currency")
  @Mapping(target = "balance", source = "initialBalance")
  Account toEntity(NewAccountDto newAccountDto);

  @Mapping(target = "id", source = "account.id")
  @Mapping(target = "status", source = "account.status")
  AccountDto toDto(Account account);
}