package app.bank.dummy.mappers;

import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.models.Transaction;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface TransactionMapper {

  @Mapping(source = "creditAccountBalance", target = "creditAccount.balance")
  @Mapping(source = "creditAccountCurrencyCode", target = "creditAccount.currency.code")
  @Mapping(source = "creditAccountId", target = "creditAccount.id")
  @Mapping(source = "debitAccountBalance", target = "debitAccount.balance")
  @Mapping(source = "debitAccountCurrencyCode", target = "debitAccount.currency.code")
  @Mapping(source = "debitAccountId", target = "debitAccount.id")
  Transaction toEntity(TransactionDto transactionDto);

  @InheritInverseConfiguration(name = "toEntity")
  TransactionDto toDto(Transaction transaction);
}