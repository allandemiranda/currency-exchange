package app.bank.dummy.mappers;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
import app.bank.dummy.entities.Client;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface ClientMapper {

  @Mapping(source = "credentialsLogin", target = "credentials.login")
  @Mapping(source = "infoLastName", target = "info.lastName")
  @Mapping(source = "infoFirstName", target = "info.firstName")
  Client toEntity(ClientDto clientDto);

  @InheritInverseConfiguration(name = "toEntity")
  ClientDto toDto(Client client);

  @Mapping(source = "credentialsPassword", target = "credentials.password")
  @Mapping(source = "credentialsLogin", target = "credentials.login")
  @Mapping(source = "infoLastName", target = "info.lastName")
  @Mapping(source = "infoFirstName", target = "info.firstName")
  Client toEntity(NewClientDto newClientDto);

  @InheritConfiguration(name = "toEntity")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Client partialUpdate(UpdateClientDto updateClientDto, @MappingTarget Client client);
}