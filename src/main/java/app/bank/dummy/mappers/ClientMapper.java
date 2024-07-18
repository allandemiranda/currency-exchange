package app.bank.dummy.mappers;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING, uses = {AccountMapper.class})
public interface ClientMapper {

  ClientDto toDto(Client client);

  @Mapping(source = "accountInitialBalance", target = "account.balance")
  @Mapping(source = "accountCurrencyCode", target = "account.currency.code")
  Client toEntity(NewClientDto newClientDto);

}