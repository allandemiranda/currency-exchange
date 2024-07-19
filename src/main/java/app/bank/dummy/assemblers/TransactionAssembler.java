package app.bank.dummy.assemblers;

import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.requests.AccountRequest;
import java.util.AbstractMap.SimpleEntry;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class TransactionAssembler implements RepresentationModelAssembler<SimpleEntry<UUID, TransactionDto>, EntityModel<TransactionDto>> {

  @Override
  public @NonNull EntityModel<TransactionDto> toModel(final @NonNull SimpleEntry<UUID, TransactionDto> simpleEntry) {
    return EntityModel.of(simpleEntry.getValue(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountRequest.class).getAccountTransaction(simpleEntry.getKey(), simpleEntry.getValue().id())).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountRequest.class).getAccountTransactions(simpleEntry.getKey())).withSelfRel());
  }
}
