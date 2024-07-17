package app.bank.dummy.mappers;

import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface TransactionMapper {

  @Mapping(source = "creditAccount.currency.code", target = "creditAccountCurrencyCode")
  @Mapping(source = "creditAccount.id", target = "creditAccountId")
  @Mapping(source = "debitAccount.currency.code", target = "debitAccountCurrencyCode")
  @Mapping(source = "debitAccount.id", target = "debitAccountId")
  TransactionDto toDto(Transaction transaction);
}