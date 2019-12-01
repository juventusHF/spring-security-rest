package ch.juventus.example.security;

import ch.juventus.example.data.Account;
import ch.juventus.example.data.AccountRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class AccountBasedUserDetailsService implements UserDetailsService {

    public static final String ROLE_S = "ROLE_%s";

    private AccountRepository accountRepository;

    public AccountBasedUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByName(username);
        if (account != null) {
            final String[] roles = account.getRoles().stream()
                    .map(r -> String.format(ROLE_S, r.getName().toUpperCase()))
                    .toArray(String[]::new); // jeez
            return new User(account.getName(), account.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList(roles));
        } else {
            throw new UsernameNotFoundException("could not find the account '" + username + "'");
        }
    }


}

