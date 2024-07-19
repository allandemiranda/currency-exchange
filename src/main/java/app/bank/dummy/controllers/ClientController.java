package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.AccountAssembler;
import app.bank.dummy.assemblers.ClientAssembler;
import app.bank.dummy.assemblers.TransactionAssembler;
import app.bank.dummy.dtos.ClientAccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.ClientTransactionDto;
import app.bank.dummy.dtos.NewAccountDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.UpdateClientDto;
import app.bank.dummy.requests.ClientRequest;
import app.bank.dummy.services.AccountService;
import app.bank.dummy.services.ClientService;
import app.bank.dummy.services.TransactionService;
import java.util.Collection;
import java.util.UUID;
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
  private final TransactionService transactionService;
  private final TransactionAssembler transactionAssembler;

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
  public EntityModel<ClientDto> getClient(final Long clientId) {
    final ClientDto clientDto = this.getClientService().getClient(clientId);
    return this.getClientAssembler().toModel(clientDto);
  }

  @Override
  public ResponseEntity<EntityModel<ClientDto>> updateClient(final Long clientId, final UpdateClientDto updateClientDto) {
    final ClientDto clientDto = this.getClientService().updateClient(clientId, updateClientDto);
    final EntityModel<ClientDto> entityModel = this.getClientAssembler().toModel(clientDto);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @Override
  public void deleteClient(final Long id) {
    this.getClientService().deactivateClient(id);
  }

  @Override
  public CollectionModel<EntityModel<ClientAccountDto>> getClientAccounts(final Long clientId) {
    final Collection<ClientAccountDto> clientAccountDtos = this.getAccountService().getClientAccounts(clientId);
    return this.getAccountAssembler().toCollectionModel(clientAccountDtos, clientId);
  }

  @Override
  public ResponseEntity<EntityModel<ClientAccountDto>> createClientAccount(final Long clientId, final NewAccountDto newAccountDto) {
    final ClientAccountDto clientAccountDto = this.getAccountService().createClientAccount(clientId, newAccountDto);
    final EntityModel<ClientAccountDto> entityModel = this.getAccountAssembler().toModel(clientAccountDto, clientId);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @Override
  public EntityModel<ClientAccountDto> getClientAccount(final Long clientId, final UUID accountId) {
    final ClientAccountDto clientAccountDto = this.getAccountService().getClientAccount(clientId, accountId);
    return this.getAccountAssembler().toModel(clientAccountDto, clientId);
  }

  @Override
  public ResponseEntity<EntityModel<ClientAccountDto>> closeClientAccount(final Long clientId, final UUID accountId) {
    final ClientAccountDto clientAccountDto = this.getAccountService().closeClientAccount(clientId, accountId);
    final EntityModel<ClientAccountDto> entityModel = this.getAccountAssembler().toModel(clientAccountDto, clientId);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @Override
  public CollectionModel<EntityModel<ClientTransactionDto>> getClientAccountTransactions(final Long clientId, final UUID accountId) {
    final Collection<ClientTransactionDto> clientTransactionList = this.getTransactionService().getClientAccountTransactions(clientId, accountId);
    return this.getTransactionAssembler().toCollectionModel(clientTransactionList, clientId, accountId);
  }

  @Override
  public ResponseEntity<EntityModel<ClientTransactionDto>> createClientAccountTransaction(final Long clientId, final UUID accountId, final NewTransactionDto newTransactionDto) {
    final ClientTransactionDto clientTransactionDto = this.getTransactionService().createClientAccountTransaction(clientId, accountId, newTransactionDto);
    final EntityModel<ClientTransactionDto> entityModel = this.getTransactionAssembler().toModel(clientTransactionDto, clientId, accountId);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
  }

  @Override
  public EntityModel<ClientTransactionDto> getClientAccountTransaction(final Long clientId, final UUID accountId, final UUID transactionId) {
    final ClientTransactionDto clientTransactionDtos = this.getTransactionService().getClientAccountTransaction(clientId, accountId, transactionId);
    return this.getTransactionAssembler().toModel(clientTransactionDtos, clientId, accountId);
  }
}
