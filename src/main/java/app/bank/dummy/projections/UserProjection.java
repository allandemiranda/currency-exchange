package app.bank.dummy.projections;

import app.bank.dummy.dtos.AccountDto;
import app.bank.dummy.models.User;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "user", types = {User.class})
public interface UserProjection {

  Long getId();

  String getName();

  String getLogin();

  AccountDto getAccount();
}
