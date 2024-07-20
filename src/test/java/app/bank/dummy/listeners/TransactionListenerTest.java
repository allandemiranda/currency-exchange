package app.bank.dummy.listeners;

import app.bank.dummy.entities.Account;
import app.bank.dummy.entities.Transaction;
import app.bank.dummy.entities.TransactionCreditInfo;
import app.bank.dummy.entities.TransactionDebitInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionListenerTest {

  @Test
  void testTempBalancesUpdated() {
    //given
    final TransactionListener transactionListener = new TransactionListener();
    final Transaction transaction = Mockito.mock(Transaction.class);
    final TransactionDebitInfo debitInfo = Mockito.spy(TransactionDebitInfo.class);
    final TransactionCreditInfo creditInfo = Mockito.spy(TransactionCreditInfo.class);
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
    Assertions.assertEquals(debitBalance, transaction.getDebitInfo().getTmpBalance());
    Assertions.assertEquals(creditBalance, transaction.getCreditInfo().getTmpBalance());
  }

  @Test
  void testNullTransactionShouldThrowException() {
    //given
    final TransactionListener transactionListener = new TransactionListener();
    //when
    final Executable executable = () -> transactionListener.prePersist(null);
    //then
    Assertions.assertThrows(NullPointerException.class, executable);
  }
}