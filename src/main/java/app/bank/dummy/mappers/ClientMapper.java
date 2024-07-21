package app.bank.dummy.mappers;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
import app.bank.dummy.entities.Client;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface ClientMapper {

  @NotNull
  @Mapping(target = "id", source = "client.id")
  @Mapping(target = "firstName", source = "client.info.firstName")
  @Mapping(target = "lastName", source = "client.info.lastName")
  @Mapping(target = "login", source = "client.credentials.login")
  @Mapping(target = "clientStatus", source = "client.info.status")
  ClientDto toDto(final @NotNull Client client);

  @NotNull
  @Mapping(target = "info.firstName", source = "firstName")
  @Mapping(target = "info.lastName", source = "lastName")
  @Mapping(target = "credentials.login", source = "login")
  @Mapping(target = "credentials.password", source = "password")
  Client toEntity(final @NotNull NewClientDto newClientDto);

  @NotNull
  @Mapping(source = "firstName", target = "info.firstName")
  @Mapping(source = "lastName", target = "info.lastName")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Client partialUpdate(final @NotNull UpdateClientDto updateClientDto, final @NotNull @MappingTarget Client client);

}