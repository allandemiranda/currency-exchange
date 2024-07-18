package app.bank.dummy.requests;


import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.CurrencyDto;
import java.util.UUID;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/accounts")
public interface AccountRequest {

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<AccountDto>> getAccounts();

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<AccountDto> getAccount(final @PathVariable UUID id);

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<EntityModel<AccountDto>> closeAccount(final @PathVariable UUID id);

  @GetMapping("/{id}/clients")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<ClientDto> getAccountsClient(@PathVariable final UUID id);

  @GetMapping("/{id}/currencies")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<CurrencyDto> getAccountsCurrency(@PathVariable final UUID id);

}
