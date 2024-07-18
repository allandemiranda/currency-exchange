package app.bank.dummy.assemblers;

import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.requests.TransactionRequest;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class TransactionAssembler implements RepresentationModelAssembler<TransactionDto, EntityModel<TransactionDto>> {

  @Override
  public @NonNull EntityModel<TransactionDto> toModel(final @NonNull TransactionDto transactionDto) {
    return EntityModel.of(transactionDto, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionRequest.class).getTransaction(transactionDto.id())).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionRequest.class).getTransactions()).withRel("transactions"));
  }
}
