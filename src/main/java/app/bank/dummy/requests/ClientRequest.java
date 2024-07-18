package app.bank.dummy.requests;

import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.dtos.NewClientDto;
import jakarta.validation.Valid;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/clients")
public interface ClientRequest {

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  Collection<ClientDto> getClients();

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ClientDto getClients(final @PathVariable Long id);

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  ClientDto createClient(final @RequestBody @Valid NewClientDto newClientDto);

}
