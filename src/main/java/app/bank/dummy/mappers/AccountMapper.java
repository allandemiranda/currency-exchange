package app.bank.dummy.mappers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING, uses = {CurrencyMapper.class})
public interface AccountMapper {

  Account toEntity(AccountDto accountDto);

}