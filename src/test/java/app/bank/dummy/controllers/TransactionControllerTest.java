package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.TransactionAssembler;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.services.TransactionService;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

  @Mock
  private TransactionService transactionService;
  @Mock
  private TransactionAssembler transactionAssembler;
  @InjectMocks
  private TransactionController transactionController;

  @Test
  void testGetTransactions() {
    //given
    final TransactionDto transactionDto1 = Mockito.mock(TransactionDto.class);
    final TransactionDto transactionDto2 = Mockito.mock(TransactionDto.class);
    final Collection<TransactionDto> transactionDtos = Arrays.asList(transactionDto1, transactionDto2);
    final CollectionModel<EntityModel<TransactionDto>> expectedCollectionModel = transactionAssembler.toCollectionModel(transactionDtos);
    //when
    Mockito.when(transactionService.getTransactions()).thenReturn(transactionDtos);
    Mockito.when(transactionAssembler.toCollectionModel(transactionDtos)).thenReturn(expectedCollectionModel);
    final CollectionModel<EntityModel<TransactionDto>> result = transactionController.getTransactions();
    //then
    Assertions.assertEquals(expectedCollectionModel, result);
  }
}