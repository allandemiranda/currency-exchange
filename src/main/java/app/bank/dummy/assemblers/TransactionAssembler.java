package app.bank.dummy.assemblers;

import app.bank.dummy.dtos.ClientTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.requests.ClientRequest;
import app.bank.dummy.requests.TransactionRequest;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class TransactionAssembler implements RepresentationModelAssembler<TransactionDto, EntityModel<TransactionDto>> {

  @Override
  public @NonNull EntityModel<TransactionDto> toModel(final @NonNull TransactionDto transactionDto) {
    return EntityModel.of(transactionDto, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionRequest.class).getTransactions()).withRel("transactions"));
  }

  public @NonNull EntityModel<ClientTransactionDto> toModel(final @NonNull ClientTransactionDto clientTransactionDto, final @NonNull Long clientId, final @NonNull UUID accountId) {
    return EntityModel.of(clientTransactionDto,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientRequest.class).getClientAccountTransaction(clientId, accountId, clientTransactionDto.id())).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClientRequest.class).getClientAccountTransactions(clientId, accountId)).withSelfRel());
  }

  public CollectionModel<EntityModel<ClientTransactionDto>> toCollectionModel(final @NonNull Iterable<ClientTransactionDto> clientTransactionDtos, final @NonNull Long clientId,
      final @NonNull UUID accountId) {
    return StreamSupport.stream(clientTransactionDtos.spliterator(), false).map(clientTransactionDto -> this.toModel(clientTransactionDto, clientId, accountId))
        .collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
  }
}
