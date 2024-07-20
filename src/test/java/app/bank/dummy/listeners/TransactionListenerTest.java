package app.bank.dummy.listeners;

import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Transaction;
import app.bank.dummy.entities.TransactionCreditInfo;
import app.bank.dummy.entities.TransactionDebitInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TransactionListenerTest {

  @Test
  void testTempBalancesUpdated() {
    //given
    final TransactionListener transactionListener = new TransactionListener();
    final Transaction transaction = Mockito.mock(Transaction.class);
    final TransactionDebitInfo debitInfo = Mockito.mock(TransactionDebitInfo.class);
    final TransactionCreditInfo creditInfo = Mockito.mock(TransactionCreditInfo.class);
    final Account debitAccount = Mockito.mock(Account.class);
    final Account creditAccount = Mockito.mock(Account.class);
    final double debitBalance = 1D;
    final double creditBalance = 2D;
    //when
    Mockito.when(transaction.getDebitInfo()).thenReturn(debitInfo);
    Mockito.when(transaction.getCreditInfo()).thenReturn(creditInfo);
    Mockito.when(debitInfo.getAccount()).thenReturn(debitAccount);
    Mockito.when(creditInfo.getAccount()).thenReturn(creditAccount);
    Mockito.when(debitAccount.getBalance()).thenReturn(debitBalance);
    Mockito.when(creditAccount.getBalance()).thenReturn(creditBalance);
    transactionListener.prePersist(transaction);
    //then
    Mockito.verify(debitInfo).setTmpBalance(debitBalance);
    Mockito.verify(creditInfo).setTmpBalance(creditBalance);
  }

  @Test
  void testNullTransactionShouldThrowException() {
    //given
    final TransactionListener transactionListener = new TransactionListener();
    //when
    Assertions.assertThrows(NullPointerException.class, () -> transactionListener.prePersist(null));
  }
}