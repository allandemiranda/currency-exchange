package app.bank.dummy.mappers;

import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.models.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface CurrencyMapper {

  Currency toEntity(CurrencyDto currencyDto);

  CurrencyDto toDto(Currency currency);

}