package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.AccountAssembler;
import app.bank.dummy.assemblers.ClientAssembler;
import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewAccountDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
import app.bank.dummy.requests.ClientRequest;
import app.bank.dummy.services.AccountService;
import app.bank.dummy.services.ClientService;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class ClientController implements ClientRequest {

  private final ClientService clientService;
  private final ClientAssembler clientAssembler;
  private final AccountService accountService;
  private final AccountAssembler accountAssembler;

  @Override
  public CollectionModel<EntityModel<ClientDto>> getClients() {
    final Collection<ClientDto> clients = this.getClientService().getClients();
    return this.getClientAssembler().toCollectionModel(clients);
  }

  @Override
  public ResponseEntity<EntityModel<ClientDto>> createClient(final NewClientDto newClientDto) {
    final ClientDto clientDto = this.getClientService().createClient(newClientDto);
    final EntityModel<ClientDto> entityModel = this.getClientAssembler().toModel(clientDto);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @Override
  public EntityModel<ClientDto> getClient(final Long id) {
    final ClientDto clientDto = this.getClientService().getClientById(id);
    return this.getClientAssembler().toModel(clientDto);
  }

  @Override
  public ResponseEntity<EntityModel<ClientDto>> updateClient(final Long id, final UpdateClientDto updateClientDto) {
    final ClientDto clientDto = this.getClientService().updateClient(id, updateClientDto);
    final EntityModel<ClientDto> entityModel = this.getClientAssembler().toModel(clientDto);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @Override
  public void deleteClient(final Long id) {
    this.getClientService().deleteClient(id);
  }

  @Override
  public CollectionModel<EntityModel<AccountDto>> getClientAccounts(final Long id) {
    final Collection<AccountDto> accountDtos = this.getClientService().getAccountsByClientId(id);
    return this.getAccountAssembler().toCollectionModel(accountDtos);
  }

  @Override
  public ResponseEntity<EntityModel<AccountDto>> createClientAccount(final Long id, final NewAccountDto newAccountDto) {
    final AccountDto accountDto = this.getAccountService().createAccount(newAccountDto, id);
    final EntityModel<AccountDto> entityModel = this.getAccountAssembler().toModel(accountDto);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }
}
