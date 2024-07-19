package app.bank.dummy.requests;


import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import java.util.UUID;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/accounts")
public interface AccountRequest {

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  CollectionModel<EntityModel<AccountDto>> getAccounts();

  @GetMapping("/{accountId}")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<AccountDto> getAccount(final @PathVariable UUID accountId);

  @GetMapping("/{accountId}/clients")
  @ResponseStatus(HttpStatus.OK)
  EntityModel<ClientDto> getAccountClient(@PathVariable final UUID accountId);

}
