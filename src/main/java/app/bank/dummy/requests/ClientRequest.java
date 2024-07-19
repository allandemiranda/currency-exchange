package app.bank.dummy.requests;

import app.bank.dummy.dtos.ClientAccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.ClientTransactionDto;
import app.bank.dummy.dtos.NewAccountDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.NewTransactionDto;
import app.bank.dummy.dtos.UpdateClientDto;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/clients")
public interface ClientRequest {

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<ClientDto>> getClients();

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<ClientDto>> createClient(final @RequestBody @Valid NewClientDto newClientDto);

  @GetMapping("/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<ClientDto> getClient(final @PathVariable Long clientId);

  @PutMapping("/{clientId}")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<ClientDto>> updateClient(final @PathVariable Long clientId, final @RequestBody @Valid UpdateClientDto updateClientDto);

  @DeleteMapping("/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  void deleteClient(final @PathVariable Long clientId);

  @GetMapping("/{clientId}/accounts")
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<ClientAccountDto>> getClientAccounts(final @PathVariable Long clientId);

  @PostMapping("/{clientId}/accounts")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<ClientAccountDto>> createClientAccount(final @PathVariable Long clientId, final @RequestBody @Valid NewAccountDto newAccountDto);

  @GetMapping("/{clientId}/accounts/{accountId}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<ClientAccountDto> getClientAccount(final @PathVariable Long clientId, final @PathVariable UUID accountId);

  @PutMapping("/{clientId}/accounts/{accountId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<EntityModel<ClientAccountDto>> closeClientAccount(final @PathVariable Long clientId, final @PathVariable UUID accountId);

  @GetMapping("/{clientId}/accounts/{accountId}/transactions")
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<ClientTransactionDto>> getClientAccountTransactions(final @PathVariable Long clientId, final @PathVariable UUID accountId);

  @PostMapping("/{clientId}/accounts/{accountId}/transactions")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<EntityModel<ClientTransactionDto>> createClientAccountTransaction(final @PathVariable Long clientId, final @PathVariable UUID accountId,
      final @RequestBody NewTransactionDto newTransactionDto);

  @GetMapping("/{clientId}/accounts/{accountId}/transactions/{transactionId}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<ClientTransactionDto> getClientAccountTransaction(final @PathVariable Long clientId, final @PathVariable UUID accountId, @PathVariable final UUID transactionId);

}
