package accounts.web;

import accounts.api.APIUser;
import accounts.model.Account;
import accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountsController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/accounts")
    public List<Account> accounts(APIUser apiUser) {
        return StreamSupport.stream(accountRepository.findAll().spliterator(), false).collect(toList());
    }

    @GetMapping("/encodePassword/{rawPassword}")
    public String encodePassword(@PathVariable("rawPassword") String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
