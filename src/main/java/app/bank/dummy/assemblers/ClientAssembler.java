package app.bank.dummy.assemblers;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.requests.ClientRequest;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ClientAssembler implements RepresentationModelAssembler<ClientDto, EntityModel<ClientDto>> {

  @Override
  public @NonNull EntityModel<ClientDto> toModel(final @NonNull ClientDto clientDto) {
    return EntityModel.of(clientDto, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientRequest.class).getClient(clientDto.id())).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientRequest.class).getClients()).withRel("clients"));
  }
}
