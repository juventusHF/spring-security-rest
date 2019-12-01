package ch.juventus.example;

import ch.juventus.example.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@SpringBootApplication
public class ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}

    @Component
    class initRepositoryCLR implements CommandLineRunner {

        private final DepartmentRepository departmentRepository;
        private final AccountRepository accountRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder encoder;

        @Autowired
        public initRepositoryCLR(DepartmentRepository departmentRepository,
                                 AccountRepository accountRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder) {
            this.departmentRepository = departmentRepository;
            this.accountRepository = accountRepository;
            this.roleRepository = roleRepository;
            this.encoder = passwordEncoder;
        }

        @Override
        public void run(String... strings) throws Exception {
            Department accounting = new Department("Accounting");
            accounting.addEmployee(new Employee("Tim", "Taylor"));
            accounting.addEmployee(new Employee("Al", "Borland"));
            accounting.addEmployee(new Employee("Wilson", "Wilson"));
            accounting.addEmployee(new Employee("Bob", "Vila"));
            departmentRepository.save(accounting);

            Role userRole = new Role("user");
            Account bob = new Account("bob", encoder.encode("secret"));
            bob.addRole(userRole);

            Role adminRole = new Role("admin");
            Account joe = new Account("joe", encoder.encode("secret"));
            joe.addRole(userRole);
            joe.addRole(adminRole);
            accountRepository.saveAll(Arrays.asList(joe, bob));
        }
    }


}
