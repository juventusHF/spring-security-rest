package ch.juventus.example.security;

import ch.juventus.example.data.Account;
import ch.juventus.example.data.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsConfiguration extends GlobalAuthenticationConfigurerAdapter {

    private AccountRepository accountRepository;

    @Autowired
    public UserDetailsConfiguration(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Account account = accountRepository.findByName(username);
                if (account != null) {
                    final String[] roles = account.getRoles().stream()
                            .map(r -> r.getName())
                            .toArray(String[]::new); // jeez
                    return new User(account.getName(), account.getPassword(), true, true, true, true,
                            AuthorityUtils.createAuthorityList(roles));
                } else {
                    throw new UsernameNotFoundException("could not find the account '" + username + "'");
                }
            }

        };
    }

}

