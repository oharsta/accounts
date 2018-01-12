package accounts.web;

import accounts.api.APIUser;
import accounts.model.Account;
import accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountsController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<Account> accounts(APIUser apiUser) {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false).collect(toList());
    }
}
