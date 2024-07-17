package app.bank.dummy.repositories;

import app.bank.dummy.models.User;
import app.bank.dummy.projections.UserProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = UserProjection.class)
public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

}