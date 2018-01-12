package accounts.repository;

import accounts.model.Account;
import accounts.model.AccountType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByIdentifierIgnoreCaseAndAccountType(String identifier, AccountType accountType);

}
