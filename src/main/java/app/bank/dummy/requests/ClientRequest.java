package app.bank.dummy.requests;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewAccountDto;
import app.bank.dummy.dtos.NewClientDto;
import app.bank.dummy.dtos.UpdateClientDto;
import jakarta.validation.Valid;
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

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<ClientDto> getClient(final @PathVariable Long id);

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<ClientDto>> updateClient(final @PathVariable Long id, final @RequestBody @Valid UpdateClientDto updateClientDto);

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  void deleteClient(final @PathVariable Long id);

  @GetMapping("/{id}/accounts")
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<AccountDto>> getClientAccounts(final @PathVariable Long id);

  @PostMapping("/{id}/accounts")
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<EntityModel<AccountDto>> createClientAccount(final @PathVariable Long id, final @RequestBody @Valid NewAccountDto newAccountDto);

}
