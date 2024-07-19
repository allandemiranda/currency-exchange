package app.bank.dummy.mappers;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
import app.bank.dummy.entities.Client;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface ClientMapper {

  @Mapping(target = "id", source = "client.id")
  @Mapping(target = "firstName", source = "client.info.firstName")
  @Mapping(target = "lastName", source = "client.info.lastName")
  @Mapping(target = "login", source = "client.credentials.login")
  ClientDto toDto(Client client);

  @Mapping(target = "info.firstName", source = "firstName")
  @Mapping(target = "info.lastName", source = "lastName")
  @Mapping(target = "credentials.login", source = "login")
  @Mapping(target = "credentials.password", source = "password")
  Client toEntity(NewClientDto newClientDto);

  @Mapping(source = "firstName", target = "info.firstName")
  @Mapping(source = "lastName", target = "info.lastName")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Client partialUpdate(UpdateClientDto updateClientDto, @MappingTarget Client client);

}