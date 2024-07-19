package app.bank.dummy.mappers;

import app.bank.dummy.dtos.ClientTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.entities.Transaction;
import app.bank.dummy.enums.TransactionType;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface TransactionMapper {

  @Mapping(target = "id", source = "transaction.id")
  @Mapping(target = "dataTime", source = "transaction.dataTime")
  @Mapping(target = "amount", source = "transaction.amount")
  @Mapping(target = "taxRate", source = "transaction.taxRate")
  @Mapping(target = "tmpBalance", source = "tmpBalance")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "account", source = "account")
  ClientTransactionDto toDto(Transaction transaction, double tmpBalance, TransactionType type, UUID account);

  @Mapping(target = "id", source = "transaction.id")
  @Mapping(target = "dataTime", source = "transaction.dataTime")
  @Mapping(target = "amount", source = "transaction.amount")
  @Mapping(target = "taxRate", source = "transaction.taxRate")
  @Mapping(target = "debitAccount", source = "transaction.debitInfo.account.id")
  @Mapping(target = "creditAccount", source = "transaction.creditInfo.account.id")
  TransactionDto toDto(Transaction transaction);
}