package app.bank.dummy.controllers;

import app.bank.dummy.assemblers.AccountAssembler;
import app.bank.dummy.assemblers.ClientAssembler;
import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.ClientDto;
import app.bank.dummy.services.AccountService;
import app.bank.dummy.services.ClientService;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
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
class AccountControllerTest {

  @Mock
  private AccountService accountService;
  @Mock
  private ClientService clientService;
  @Mock
  private AccountAssembler accountAssembler;
  @Mock
  private ClientAssembler clientAssembler;
  @InjectMocks
  private AccountController accountController;

  @Test
  void testGetAccounts() {
    //given
    final AccountDto accountDto1 = Mockito.mock(AccountDto.class);
    final AccountDto accountDto2 = Mockito.mock(AccountDto.class);
    final Collection<AccountDto> accountDtos = Arrays.asList(accountDto1, accountDto2);
    final CollectionModel<EntityModel<AccountDto>> expectedCollectionModel = accountAssembler.toCollectionModel(accountDtos);
    //when
    Mockito.when(accountService.getAccounts()).thenReturn(accountDtos);
    Mockito.when(accountAssembler.toCollectionModel(accountDtos)).thenReturn(expectedCollectionModel);
    final CollectionModel<EntityModel<AccountDto>> result = accountController.getAccounts();
    //then
    Assertions.assertEquals(expectedCollectionModel, result);
  }

  @Test
  void testGetAccount() {
    //given
    final UUID id = UUID.randomUUID();
    final AccountDto accountDto = Mockito.mock(AccountDto.class);
    final EntityModel<AccountDto> expectedEntityModel = EntityModel.of(accountDto);
    //when
    Mockito.when(accountService.getAccount(id)).thenReturn(accountDto);
    Mockito.when(accountAssembler.toModel(accountDto)).thenReturn(expectedEntityModel);
    final EntityModel<AccountDto> result = accountController.getAccount(id);
    //then
    Assertions.assertEquals(expectedEntityModel, result);
  }

  @Test
  void testGetAccountClient() {
    //given
    final UUID accountId = UUID.randomUUID();
    final ClientDto clientDto = Mockito.mock(ClientDto.class);
    final EntityModel<ClientDto> expectedEntityModel = EntityModel.of(clientDto);
    //when
    Mockito.when(clientService.getClientByAccountId(accountId)).thenReturn(clientDto);
    Mockito.when(clientAssembler.toModel(clientDto)).thenReturn(expectedEntityModel);
    final EntityModel<ClientDto> result = accountController.getAccountClient(accountId);
    //then
    Assertions.assertEquals(expectedEntityModel, result);
  }
}