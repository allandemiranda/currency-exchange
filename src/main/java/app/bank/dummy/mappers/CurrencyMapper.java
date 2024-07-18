package app.bank.dummy.mappers;

import app.bank.dummy.dtos.CurrencyDto;
import app.bank.dummy.dtos.NewCurrencyDto;
import app.bank.dummy.dtos.UpdateCurrencyDto;
import app.bank.dummy.entities.Currency;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface CurrencyMapper {

  Currency toEntity(CurrencyDto currencyDto);

  CurrencyDto toDto(Currency currency);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Currency partialUpdate(UpdateCurrencyDto updateCurrencyDto, @MappingTarget Currency currency);

  Currency toEntity(NewCurrencyDto newCurrencyDto);
}