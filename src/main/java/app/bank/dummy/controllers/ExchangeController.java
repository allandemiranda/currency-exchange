package app.bank.dummy.controllers;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.dtos.OpenTransactionDto;
import app.bank.dummy.dtos.TransactionDto;
import app.bank.dummy.requests.ExchangeRequest;
import app.bank.dummy.services.CurrencyService;
import app.bank.dummy.services.TransactionService;
import app.bank.dummy.services.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE)
public class ExchangeController implements ExchangeRequest {

  private final UserService userService;
  private final CurrencyService currencyService;
  private final TransactionService transactionService;

  @Override
  public TransactionDto createTransaction(final @NonNull OpenTransactionDto openTransactionDto) {
    final AccountDto debitAccount = this.getUserService().getAccountByUserId(openTransactionDto.debitUserId());
    final AccountDto creditAccount = this.getUserService().getAccountByUserId(openTransactionDto.creditUserId());
    final double creditAmount = this.getCurrencyService().getCreditAmount(debitAccount.currency(), creditAccount.currency(), openTransactionDto.amount());
    final double debitAmount = openTransactionDto.amount() * (-1.0);
    return this.getTransactionService().createTransaction(debitAccount, creditAccount, debitAmount, creditAmount);
  }
}
