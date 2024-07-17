package app.bank.dummy.controllers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.OpenTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.requests.TransactionRequest;
import app.bank.dummy.services.ClientService;
import app.bank.dummy.services.CurrencyService;
import app.bank.dummy.services.TransactionService;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class TransactionController implements TransactionRequest {

  private final TransactionService transactionService;
  private final ClientService clientService;
  private final CurrencyService currencyService;

  @Override
  public Collection<TransactionDto> getTransactions() {
    return this.getTransactionService().getAllTransactions();
  }

  @Override
  public TransactionDto createTransaction(final @NonNull OpenTransactionDto openTransactionDto) {
    final AccountDto debitAccount = this.getClientService().getClientById(openTransactionDto.debitClientId()).account();
    final AccountDto creditAccount = this.getClientService().getClientById(openTransactionDto.creditClientId()).account();
    final double taxRate = this.getCurrencyService().getTaxRate(debitAccount.currency(), creditAccount.currency());
    return this.getTransactionService().createTransaction(debitAccount, creditAccount, openTransactionDto.amount(), taxRate);
  }
}
