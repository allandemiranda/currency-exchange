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

  /**
   * @return All Clients on the system
   */
  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<ClientDto>> getClients();

  /**
   * @param newClientDto Inputs necessary to create a new Client
   * @return The new Client
   */
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<ClientDto>> createClient(final @RequestBody @Valid NewClientDto newClientDto);

  /**
   * @param clientId The Client ID
   * @return The Client information
   */
  @GetMapping("/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<ClientDto> getClient(final @PathVariable Long clientId);

  /**
   * @param clientId The Client ID to be updated
   * @param updateClientDto The new parameters to be updated
   * @return The Client updated
   */
  @PutMapping("/{clientId}")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<ClientDto>> updateClient(final @PathVariable Long clientId, final @RequestBody @Valid UpdateClientDto updateClientDto);

  /**
   * @param clientId The Client ID to be disabled
   */
  @DeleteMapping("/{clientId}")
  @ResponseStatus(HttpStatus.OK)
  void deleteClient(final @PathVariable(name = "clientId") Long clientId);

  /**
   * @param clientId The Client ID
   * @return List of accounts from this Client
   */
  @GetMapping("/{clientId}/accounts")
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<ClientAccountDto>> getClientAccounts(final @PathVariable Long clientId);

  /**
   * @param clientId The Client ID to open a new Account ID
   * @param newAccountDto The parameters necessary to open a new Account to the Client
   * @return The new Account from the Client
   */
  @PostMapping("/{clientId}/accounts")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<ClientAccountDto>> createClientAccount(final @PathVariable Long clientId, final @RequestBody @Valid NewAccountDto newAccountDto);

  /**
   * @param clientId The Client ID linked with Account ID
   * @param accountId The Account ID linked with Client ID
   * @return The Account information
   */
  @GetMapping("/{clientId}/accounts/{accountId}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<ClientAccountDto> getClientAccount(final @PathVariable Long clientId, final @PathVariable UUID accountId);

  /**
   * @param clientId The Client ID linked with Account ID
   * @param accountId The Account ID linked with Client that will be CLOSE
   * @return The closed Account information
   */
  @PutMapping("/{clientId}/accounts/{accountId}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<EntityModel<ClientAccountDto>> closeClientAccount(final @PathVariable Long clientId, final @PathVariable UUID accountId);

  /**
   * @param clientId The Client ID linked with Account ID
   * @param accountId The Account ID linked with Client ID
   * @return The historic Transactions from this Account
   */
  @GetMapping("/{clientId}/accounts/{accountId}/transactions")
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<ClientTransactionDto>> getClientAccountTransactions(final @PathVariable Long clientId, final @PathVariable UUID accountId);

  /**
   * @param clientId The Client ID linked with Account ID
   * @param accountId The Account ID linked with Client ID
   * @param newTransactionDto Inputs necessary to open one transaction between this Account and other Account from this system
   * @return The Transaction done
   */
  @PostMapping("/{clientId}/accounts/{accountId}/transactions")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<EntityModel<ClientTransactionDto>> createClientAccountTransaction(final @PathVariable Long clientId, final @PathVariable UUID accountId,
      final @RequestBody NewTransactionDto newTransactionDto);

  /**
   * @param clientId The Client ID linked with Account ID
   * @param accountId The Account ID linked with Client ID
   * @param transactionId The Transaction ID linked with Account ID
   * @return The information from this Transaction
   */
  @GetMapping("/{clientId}/accounts/{accountId}/transactions/{transactionId}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<ClientTransactionDto> getClientAccountTransaction(final @PathVariable Long clientId, final @PathVariable UUID accountId, @PathVariable final UUID transactionId);

}
